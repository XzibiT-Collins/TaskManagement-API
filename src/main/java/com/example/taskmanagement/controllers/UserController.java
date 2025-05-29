package com.example.taskmanagement.controllers;

import com.example.taskmanagement.dto.request.UserRequestDto;
import com.example.taskmanagement.dto.response.TaskResponseDto;
import com.example.taskmanagement.dto.response.UserResponseDto;
import com.example.taskmanagement.exceptions.UserNotFoundException;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestMapping(path = "/User")
@RestController
public class UserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/addUser")
    protected ResponseEntity<?> addUser(@RequestBody UserRequestDto userRequestDto){
        if(userRequestDto.getFirstName().isEmpty() ||
                userRequestDto.getLastName().isEmpty() ||
                userRequestDto.getUserName().isEmpty() ||
                userRequestDto.getUserEmail().isEmpty() ||
                userRequestDto.getUserPassword().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing Required Fields");
        }

        User newUser = new User();
        newUser.setUserName(userRequestDto.getUserName());
        newUser.setFirstName(userRequestDto.getFirstName());
        newUser.setLastName(userRequestDto.getLastName());
        newUser.setUserPassword(userRequestDto.getUserPassword());
        newUser.setUserEmail(userRequestDto.getUserEmail());

        User user = userService.addUser(newUser);
        logger.info("User added successfully");
        //save user
        return ResponseEntity.ok(new UserResponseDto(
             user.getId(),
             user.getUserName(),
             user.getUserEmail(),
             user.getTasks()
        ));
    }

    @GetMapping(path = "/userList")
    protected ResponseEntity<?> getUserList(){
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users
                .stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUserName(),
                        user.getUserEmail(),
                        user.getTasks()
                ))
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/getUser")
    protected ResponseEntity<?> getUser(@RequestParam Integer userId){
        try{
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(
                    new UserResponseDto(
                            user.getId(),
                            user.getUserName(),
                            user.getUserEmail(),
                            user.getTasks()
                    )
            );
        }catch (UserNotFoundException e){
            logger.info(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/getUserTasks")
    protected ResponseEntity<?> getUserTasks(@RequestParam Integer userId){
        try{
            List<Task> tasks = userService.getUserById(userId).getTasks();
            return ResponseEntity.ok(
                    tasks
                            .stream()
                            .map(task -> new TaskResponseDto(
                                    task.getId(),
                                    task.getTaskName(),
                                    task.getTaskDescription(),
                                    task.getTaskStatus(),
                                    task.getTaskPriority(),
                                    task.getTaskDueDate(),
                                    task.getTaskCreatedBy()
                            )).collect(Collectors.toList())
            );

        }catch (UserNotFoundException e){
            logger.info(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteUser")
    protected ResponseEntity<?> deleteUser(@RequestParam Integer userId){
        if(userId == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User ID cannot be null");
        }

        userService.deleteUser(userId);
        return ResponseEntity
                .ok("User with ID: "+ userId + " deleted successfully");

    }
}
