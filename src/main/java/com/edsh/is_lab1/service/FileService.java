package com.edsh.is_lab1.service;

import com.edsh.is_lab1.dto.FileDTO;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.model.Dragon;
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
        dragonService.addDragons(dragons, user);
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

}
