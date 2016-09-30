package com.anahoret.repository;

import com.anahoret.model.TaskFlag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskFlagRepository extends JpaRepository<TaskFlag, Long> {
    TaskFlag findOneByName(String name);
}
