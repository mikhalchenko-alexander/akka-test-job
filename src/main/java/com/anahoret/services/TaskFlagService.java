package com.anahoret.services;

import com.anahoret.model.TaskFlag;
import com.anahoret.repository.TaskFlagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskFlagService {

    private TaskFlagRepository taskFlagRepository;

    public TaskFlagService(TaskFlagRepository taskFlagRepository) {
        this.taskFlagRepository = taskFlagRepository;
    }

    @Transactional(readOnly = true)
    public boolean isFlagEnabled(String name) {
        return taskFlagRepository.findOneByName(name).getValue();
    }

    @Transactional
    public void setFlagEnabled(String name, boolean enabled) {
        TaskFlag flag = taskFlagRepository.findOneByName(name);
        flag.setValue(enabled);
        taskFlagRepository.save(flag);
    }

}
