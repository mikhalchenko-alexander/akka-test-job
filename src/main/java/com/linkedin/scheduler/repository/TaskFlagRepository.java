package com.linkedin.scheduler.repository;

import com.linkedin.scheduler.model.TaskFlag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskFlagRepository extends JpaRepository<TaskFlag, Long> {
    TaskFlag findOneByName(String name);
}
