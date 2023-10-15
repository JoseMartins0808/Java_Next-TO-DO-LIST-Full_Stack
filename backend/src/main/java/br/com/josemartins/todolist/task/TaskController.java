package br.com.josemartins.todolist.task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.josemartins.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private ITaskRepository taskRepository;

    public TaskController(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    @PostMapping
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        final Object userId = request.getAttribute("userId");

        taskModel.setUserId((UUID) userId); // Tipagem forçada

        final LocalDateTime currenDateTime = LocalDateTime.now();

        if(currenDateTime.isAfter(taskModel.getStartAt()) || currenDateTime.isAfter(taskModel.getEndAt())){
            final HashMap<String, String> errorMessage = new HashMap<String, String>();
            errorMessage.put("message", "Start at / end at dates must be after than current date");
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            System.out.println(errorMessage);
            return null;
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            final HashMap<String, String> errorMessage = new HashMap<String, String>();
            errorMessage.put("message", "Start at date must be earlier than end at date");
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            System.out.println(errorMessage);
            return null;
        }
        final TaskModel newTask = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(newTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskModel>> findAll (HttpServletRequest request) {

        final Object userId = request.getAttribute("userId");

        final List<TaskModel> allUserTasks = this.taskRepository.findByUserId((UUID) userId);

        return ResponseEntity.status(HttpStatus.OK).body(allUserTasks);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskModel> update (@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID taskId) {

        final TaskModel taskFound = this.taskRepository.findById(taskId).orElse(null);

        final Object userId = request.getAttribute("userId");

        if(!taskFound.getUserId().equals(userId)){
            final HashMap<String, String> errorMessage = new HashMap<String, String>();
            errorMessage.put("message", "Task does not belongs to this user");
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            System.out.println(errorMessage);
            return null;
        }

        Utils.copyNotNullProperties(taskModel, taskFound);

        final TaskModel updateTask = this.taskRepository.save(taskFound);


        return ResponseEntity.status(HttpStatus.OK).body(updateTask);
    }
}
