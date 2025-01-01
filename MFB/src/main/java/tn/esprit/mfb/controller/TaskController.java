package tn.esprit.mfb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.TaskService;
import tn.esprit.mfb.entity.Task;
import tn.esprit.mfb.entity.TaskStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/gettask/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/newtask/{id}")
//    public ResponseEntity<Task> createTask(@RequestBody Task task) {
//        Task createdTask = taskService.createTask(task);
//        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
//    }


    @PostMapping("/newTask/{assignedUserId}/{complaintId}")
    public ResponseEntity<?> addNewTask(@PathVariable Long assignedUserId,
                                        @PathVariable Integer complaintId,
                                        @RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            Optional<String> description = Optional.ofNullable((String) request.get("description"));
            int effort = (int) request.get("effort");
            Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) request.get("dueDate"));

            taskService.createTask(name, description, effort, dueDate, assignedUserId, complaintId);

            return ResponseEntity.ok("Task added successfully.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid data format.");
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add task: " + e.getMessage());
        }
    }





//    @PutMapping("/{id}")
//    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
//        Task updatedTask = taskService.updateTask(id, task);
//        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
//        taskService.deleteTask(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable("id") Long id) {
        Task completedTask = taskService.markTaskAsCompleted(id);
        return new ResponseEntity<>(completedTask, HttpStatus.OK);
    }
    @PutMapping("/accept/{id}")
    public ResponseEntity<Task> acceptTask(@PathVariable("id") Long id) {
        Task completedTask = taskService.acceptTaskAs(id);
        return new ResponseEntity<>(completedTask, HttpStatus.OK);
    }
    @PutMapping("/end/{id}")
    public ResponseEntity<?> endTask(@PathVariable("id") Long id, @RequestBody Map<String, String> request) {
        try {
            String statusString = request.get("status");
            TaskStatus status = TaskStatus.valueOf(statusString.toUpperCase());
            Task endedTask = taskService.endTaskAs(id, status);
            return new ResponseEntity<>(endedTask, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status: ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to end task: " + e.getMessage());
        }
    }

}