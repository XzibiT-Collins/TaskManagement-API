package com.example.taskmanagement.dto.response;

import com.example.taskmanagement.models.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class UserResponseDto {
    private Integer id;
    private String userName;
    private String userEmail;
    private List<Task> tasks;

    public UserResponseDto(Integer id,String userName, String userEmail, List<Task> tasks) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.tasks = tasks;
    }

    public UserResponseDto() {}
}
