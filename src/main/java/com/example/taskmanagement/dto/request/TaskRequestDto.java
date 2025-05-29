package com.example.taskmanagement.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TaskRequestDto {
    @Setter
    private String taskName;
    @Setter
    private String taskDescription;
    @Setter
    private String taskStatus;
    @Setter
    private Integer taskPriority;
    @Setter
    private String taskDueDate;
    @Setter
    private Integer taskCreatedBy;
}
