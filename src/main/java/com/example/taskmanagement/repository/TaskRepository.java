package com.example.taskmanagement.repository;

import com.example.taskmanagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findTaskById(Integer id);
}
