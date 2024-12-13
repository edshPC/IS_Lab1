package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.dto.DataResponse;
import com.edsh.is_lab1.dto.FileDTO;
import com.edsh.is_lab1.dto.SimpleResponse;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/get-all")
    public ResponseEntity<?> get(@AuthenticationPrincipal User user) {
        return DataResponse.success(fileService.getAllFiles(user));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile file,
                                    @AuthenticationPrincipal User user) {
        fileService.uploadUserFile(file, user);
        return SimpleResponse.success("Файл " + file.getOriginalFilename() + " загружен");
    }

    @PostMapping("/create-dragons")
    public ResponseEntity<?> create(@RequestBody FileDTO file,
                                    @AuthenticationPrincipal User user) {
        int n = fileService.createDragons(file, user);
        return SimpleResponse.success("Успешно создано " + n + " драконов из файла " + file.getName());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody FileDTO file,
                                    @AuthenticationPrincipal User user) {
        fileService.deleteFile(file, user);
        return SimpleResponse.success("Файл " + file.getName() + " удалён");
    }

    @GetMapping("/get-history")
    public ResponseEntity<?> getHistory(@AuthenticationPrincipal User user) {
        return DataResponse.success(fileService.getImportHistory(user));
    }

}
