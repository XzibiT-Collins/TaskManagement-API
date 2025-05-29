package com.example.taskmanagement.services;

import com.example.taskmanagement.exceptions.UserNotFoundException;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    //constructor injection of repository
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Integer id){
        User user =  userRepository.findUserById(id);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}
