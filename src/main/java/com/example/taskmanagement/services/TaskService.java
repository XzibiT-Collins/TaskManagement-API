package com.example.taskmanagement.services;

import com.example.taskmanagement.dto.request.TaskRequestDto;
import com.example.taskmanagement.exceptions.TaskNotFoundException;
import com.example.taskmanagement.exceptions.UserNotFoundException;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task addTask(TaskRequestDto task){
        if(task == null){
            throw new RuntimeException("Task is null");
        }

        Task newTask = new Task();
        newTask.setTaskName(task.getTaskName());
        newTask.setTaskDescription(task.getTaskDescription());
        newTask.setTaskStatus(task.getTaskStatus());
        newTask.setTaskPriority(task.getTaskPriority());
        newTask.setTaskCreatedBy(userService.getUserById(task.getTaskCreatedBy()));
        newTask.setTaskDueDate(Date.valueOf(task.getTaskDueDate()));

        if(newTask.getTaskCreatedBy() == null){
            throw new UserNotFoundException("User not found");
        }

        return taskRepository.save(newTask);
    }

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public Task getTaskById(Integer taskId){
        Task task = taskRepository.findTaskById(taskId);
        if(task == null){
            throw new TaskNotFoundException("Task not found");
        }
        return task;
    }

    public List<Task> getTaskList(){
        return taskRepository.findAll();
    }

    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }
}
