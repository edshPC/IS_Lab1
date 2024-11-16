package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByLogin(String login);
    long countByPermission(User.Permission permission);

}
