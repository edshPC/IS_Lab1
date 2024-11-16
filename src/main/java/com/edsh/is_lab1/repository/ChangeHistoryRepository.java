package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.entity.ChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeHistoryRepository extends JpaRepository<ChangeHistory, Long> {

}
