package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.entity.ImportHistory;
import com.edsh.is_lab1.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportHistoryRepository extends JpaRepository<ImportHistory, Long> {
    List<ImportHistory> findAllByOrderByImportedAtDesc();
    List<ImportHistory> findAllByImportedByOrderByImportedAtDesc(User user);
}
