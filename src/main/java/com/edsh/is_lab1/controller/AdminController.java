package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.dto.DataResponse;
import com.edsh.is_lab1.dto.SimpleResponse;
import com.edsh.is_lab1.entity.AdminApplication;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@AuthenticationPrincipal User user) {
        adminService.createAdminApplication(user);
        return SimpleResponse.success("Заявка на администратора подана");
    }

    @GetMapping("/get-applications")
    public ResponseEntity<?> get(@AuthenticationPrincipal User user) {
        adminService.requireAdminPermission(user);
        return DataResponse.success(adminService.getAllApplications());
    }

    @PostMapping("/accept")
    public ResponseEntity<?> accept(@RequestBody AdminApplication application, @AuthenticationPrincipal User user) {
        adminService.requireAdminPermission(user);
        adminService.acceptAdminApplication(application);
        return SimpleResponse.success("Заявка пользователя " + application.getUser().getLogin() + " принята");
    }

    @PostMapping("/decline")
    public ResponseEntity<?> decline(@RequestBody AdminApplication application, @AuthenticationPrincipal User user) {
        adminService.requireAdminPermission(user);
        adminService.declineAdminApplication(application);
        return SimpleResponse.success("Заявка пользователя " + application.getUser().getLogin() + " отклонена");
    }

}
