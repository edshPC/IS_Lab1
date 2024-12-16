package com.edsh.is_lab1.service;

import com.edsh.is_lab1.dto.FileDTO;
import com.edsh.is_lab1.entity.ImportHistory;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.model.Dragon;
import com.edsh.is_lab1.repository.ImportHistoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final ObjectMapper objectMapper;
    private final DragonService dragonService;
    private final ImportHistoryRepository importHistoryRepository;
    @Setter(onMethod_ = {@Autowired, @Lazy})
    private FileService fileService;

    @SneakyThrows
    public String createUserBucketIfNotExists(User user) {
        String bucket = "u" + user.getUsername();
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
        return bucket;
    }

    @SneakyThrows
    public List<FileDTO> getAllFiles(User user) {
        String bucket = createUserBucketIfNotExists(user);
        var args = ListObjectsArgs.builder().bucket(bucket).build();
        return StreamSupport.stream(minioClient.listObjects(args).spliterator(), false)
                .map(FileDTO::fromItemResult)
                .toList();
    }

    @SneakyThrows
    public byte[] readFile(String filename, User user) {
        String bucket = createUserBucketIfNotExists(user);
        var object = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(filename)
                .build());
        return object.readAllBytes();
    }

    public int uploadUserFile(MultipartFile file, User user) {
        var importHistory = new ImportHistory();
        importHistory.setFileName(file.getOriginalFilename());
        importHistory.setImportedBy(user);
        try {
            int n = fileService.importUserFile(file, user);
            importHistory.setStatus(ImportHistory.Status.COMMITTED);
            importHistory.setEntities(n);
            return n;
        } finally {
            importHistoryRepository.save(importHistory);
        }
    }

    @SneakyThrows
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public int importUserFile(MultipartFile file, User user) {
        var dragons = objectMapper.readValue(file.getBytes(), new TypeReference<List<Dragon>>() {
        });
        dragonService.addDragons(dragons, user);
        fileService.putUserFile(file, user);
        return dragons.size();
    }

    @SneakyThrows
    public void putUserFile(MultipartFile file, User user) {
        String bucket = createUserBucketIfNotExists(user);
        var list = getAllFiles(user).stream().map(FileDTO::getName).toList();
        String ori = file.getOriginalFilename();
        if (ori == null) ori = "unknown";
        String name = ori;
        for (int i = 2; list.contains(name); i++) {
            String[] names = ori.split("\\.");
            names[0] += " (" + i + ")";
            name = String.join(".", names);
        }
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(name)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }


    @SneakyThrows
    public void deleteFile(FileDTO file, User user) {
        String bucket = createUserBucketIfNotExists(user);
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(file.getName())
                .build());
    }

    public List<ImportHistory> getImportHistory(User user) {
        if (user.getPermission() == User.Permission.ADMIN) {
            return importHistoryRepository.findAllByOrderByImportedAtDesc();
        }
        return importHistoryRepository.findAllByImportedByOrderByImportedAtDesc(user);
    }

}
