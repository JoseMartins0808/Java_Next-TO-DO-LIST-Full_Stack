package br.com.josemartins.todolist.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private ITaskRepository taskRepository;

    public TaskController(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    @PostMapping
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel) {

        System.out.println(taskModel);

        final TaskModel newTask = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(newTask);
    }

}
