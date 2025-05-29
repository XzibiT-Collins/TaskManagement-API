package com.example.taskmanagement.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Entity
public class Task {
    //getters and setters
    //setters
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Setter
    private String taskName;

    @Setter
    private String taskDescription;

    @Setter
    private String taskStatus;

    @Setter
    private Integer taskPriority;

    @Setter
    private Date taskDueDate;

    @Setter
    @ManyToOne //Foreign key relationship b/n task and user
    @JoinColumn(name = "taskCreatedBy_id")
    @JsonBackReference //Prevents backward referencing to parent -- infinite looping
    private User taskCreatedBy;

    //constructor
    public Task() {

    }

}
