package com.edsh.is_lab1.service;

import com.edsh.is_lab1.entity.AdminApplication;
import com.edsh.is_lab1.entity.ChangeHistory;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.exception.AppException;
import com.edsh.is_lab1.repository.AdminApplicationRepository;
import com.edsh.is_lab1.repository.ChangeHistoryRepository;
import com.edsh.is_lab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminApplicationRepository adminApplicationRepository;
    private final UserRepository userRepository;
    private final ChangeHistoryRepository changeHistoryRepository;

    public List<AdminApplication> getAllApplications() {
        return adminApplicationRepository.findAll();
    }

    public void requireAdminPermission(User user) {
        if (user.getPermission() != User.Permission.ADMIN)
            throw new AppException("You don't have enough permission");
    }

    public void createAdminApplication(User user) {
        if (user.getPermission() == User.Permission.ADMIN) {
            throw new AppException("You are already an admin");
        }
        if (adminApplicationRepository.findByUser(user).isPresent()) {
            throw new AppException("You have already sent an admin application");
        }
        var application = new AdminApplication();
        application.setUser(user);
        application = adminApplicationRepository.save(application);
        if (userRepository.countByPermission(User.Permission.ADMIN) == 0) {
            acceptAdminApplication(application); // Если нет админов
        }
    }

    public void acceptAdminApplication(AdminApplication application) {
        User user = application.getUser();
        user.setPermission(User.Permission.ADMIN);
        userRepository.save(user);
        adminApplicationRepository.delete(application);
    }

    public void declineAdminApplication(AdminApplication application) {
        adminApplicationRepository.delete(application);
    }

    public List<ChangeHistory> getLastChangeHistory(int limit) {
        return changeHistoryRepository
                .findAllByOrderByChangedAtDesc(PageRequest.of(0, limit));
    }

}
