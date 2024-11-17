package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.entity.ChangeHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeHistoryRepository extends JpaRepository<ChangeHistory, Long> {
    List<ChangeHistory> findAllByOrderByChangedAtDesc(Pageable pageable);
}
