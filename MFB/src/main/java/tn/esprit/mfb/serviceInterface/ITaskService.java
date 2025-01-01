package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.Task;
import tn.esprit.mfb.entity.TaskStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ITaskService {





    void createTask(String name, Optional<String> description, int effort, Date dueDate, Long assignedUserId, Integer complaintId);


    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);


    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

    Task markTaskAsCompleted(Long id);

    Task acceptTaskAs(Long id);

    Task endTaskAs(Long id, TaskStatus status);
}
