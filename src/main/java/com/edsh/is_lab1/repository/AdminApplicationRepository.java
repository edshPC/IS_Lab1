package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.entity.AdminApplication;
import com.edsh.is_lab1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminApplicationRepository extends JpaRepository<AdminApplication, Long> {
    Optional<AdminApplication> findByUser(User user);
}
