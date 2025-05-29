package com.example.taskmanagement.controllers;

import com.example.taskmanagement.dto.request.TaskRequestDto;
import com.example.taskmanagement.dto.response.TaskResponseDto;
import com.example.taskmanagement.exceptions.TaskNotFoundException;
import com.example.taskmanagement.exceptions.UserNotFoundException;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestMapping(path = "/Task")
@RestController
public class TaskController {
    private final Logger logger = Logger.getLogger(TaskController.class.getName());

    private final TaskService taskService;
    private final UserService userService;


    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping(path = "/addTask")
    protected ResponseEntity<?> addTask(@RequestBody TaskRequestDto task) {

        //Input validation
        if (task.getTaskName().isEmpty() || task.getTaskDescription().isEmpty() ||
                task.getTaskStatus().isEmpty() || task.getTaskPriority() == null ||
                task.getTaskDueDate() == null || task.getTaskCreatedBy() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing Required Fields");
        }
        //get createdUser

        try{
            //save the task in db
            Task newTask = taskService.addTask(task);
            logger.info("User found proceeding to save task");
            return ResponseEntity.ok(
                    new TaskResponseDto(
                            newTask.getId(),
                            newTask.getTaskName(),
                            newTask.getTaskDescription(),
                            newTask.getTaskStatus(),
                            newTask.getTaskPriority(),
                            newTask.getTaskDueDate(),
                            newTask.getTaskCreatedBy()
                    )
            );
        }catch (UserNotFoundException e){
            logger.info(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/getTask")
    protected ResponseEntity<?> getTask(@RequestParam Integer taskId){
        try{
            Task task = taskService.getTaskById(taskId);
            return ResponseEntity.ok(
                    new TaskResponseDto(
                            task.getId(),
                            task.getTaskName(),
                            task.getTaskDescription(),
                            task.getTaskStatus(),
                            task.getTaskPriority(),
                            task.getTaskDueDate(),
                            task.getTaskCreatedBy()
                    )
            );
        }catch (TaskNotFoundException e){
            logger.info(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/taskList")
    protected ResponseEntity<?> getTaskList(){
        List<Task> tasks = taskService.getTaskList();
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
                        ))
                        .collect(Collectors.toList())
        );
    }

    @PutMapping(path = "/updateTask/{id}")
    protected ResponseEntity<?> updateTask( @PathVariable Integer id, @RequestBody TaskRequestDto task){
        if(task.getTaskName().isEmpty() || task.getTaskDescription().isEmpty() ||
                task.getTaskStatus().isEmpty() || task.getTaskPriority() == null ||
                task.getTaskDueDate().isEmpty() || task.getTaskCreatedBy() == null ){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing Required Fields");
        }

        //get Task to update
        Task taskToUpdate =taskService.getTaskById(id);
        if(taskToUpdate == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Task not found");
        }

        //get assigned user
        User user = userService.getUserById(task.getTaskCreatedBy());
        if(user == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        //update task
        taskToUpdate.setTaskName(task.getTaskName());
        taskToUpdate.setTaskDescription(task.getTaskDescription());
        taskToUpdate.setTaskStatus(task.getTaskStatus());
        taskToUpdate.setTaskPriority(task.getTaskPriority());
        taskToUpdate.setTaskDueDate(Date.valueOf(task.getTaskDueDate()));
        taskToUpdate.setTaskCreatedBy(user);

        Task newTask = taskService.updateTask(taskToUpdate);
        return ResponseEntity.ok(
                new TaskResponseDto(
                        newTask.getId(),
                        newTask.getTaskName(),
                        newTask.getTaskDescription(),
                        newTask.getTaskStatus(),
                        newTask.getTaskPriority(),
                        newTask.getTaskDueDate(),
                        newTask.getTaskCreatedBy()
                )
        );

    }

    @DeleteMapping(path = "/deleteTask")
    protected ResponseEntity<?> deleteTask(@RequestParam Integer taskId){
        if(taskId ==  null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Task ID cannot be null.");
        }

        taskService.deleteTask(taskId);
        return ResponseEntity
                .ok("Task with ID: "+ taskId + " deleted successfully.");
    }
}
