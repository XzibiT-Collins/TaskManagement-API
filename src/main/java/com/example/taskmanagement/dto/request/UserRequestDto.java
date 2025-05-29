package com.example.taskmanagement.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequestDto {
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String userName;
    @Setter
    private String userEmail;
    @Setter
    private String userPassword;
}
