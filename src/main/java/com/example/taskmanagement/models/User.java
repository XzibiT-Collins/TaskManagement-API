package com.example.taskmanagement.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
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

    //Bidirectional relationship b/n tasks and user
    @Setter
    @OneToMany(mappedBy = "taskCreatedBy")
    @JsonManagedReference //prevents infinite looping on children that references the parent
    private List<Task> tasks;

    //constructor
    public User() {

    }
}
