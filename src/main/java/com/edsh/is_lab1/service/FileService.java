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
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
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
    public void uploadUserFile(MultipartFile file, User user) {
        String bucket = createUserBucketIfNotExists(user);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(file.getOriginalFilename())
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }

    @SneakyThrows
    public int createDragons(FileDTO file, User user) {
        String bucket = createUserBucketIfNotExists(user);
        var object = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(file.getName())
                .build());
        var dragons = objectMapper.readValue(object, new TypeReference<List<Dragon>>() {});
        object.close();
        var importHistory = new ImportHistory();
        importHistory.setFileName(file.getName());
        importHistory.setImportedBy(user);
        try {
            dragonService.addDragons(dragons, user);
            importHistory.setStatus(ImportHistory.Status.COMMITTED);
            importHistory.setEntities(dragons.size());
        } finally {
            importHistoryRepository.save(importHistory);
        }
        return dragons.size();
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
