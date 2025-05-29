package com.example.taskmanagement.dto.response;

import com.example.taskmanagement.models.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
public class TaskResponseDto {
    private Integer id;
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private Integer taskPriority;
    private Date taskDueDate;
    private User taskCreatedBy;

    public TaskResponseDto(Integer id,String taskName,
                           String taskDescription, String taskStatus,
                           Integer taskPriority, Date taskDueDate, User taskCreatedBy) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskPriority = taskPriority;
        this.taskDueDate = taskDueDate;
        this.taskCreatedBy = taskCreatedBy;
    }

    public TaskResponseDto() {}
}
