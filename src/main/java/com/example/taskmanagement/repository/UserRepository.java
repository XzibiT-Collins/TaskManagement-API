package com.example.taskmanagement.repository;

import com.example.taskmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);
}
