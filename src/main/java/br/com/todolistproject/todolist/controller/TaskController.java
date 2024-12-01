package br.com.todolistproject.todolist.controller;

import br.com.todolistproject.todolist.model.Task;
import br.com.todolistproject.todolist.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class TaskController {

    TaskService taskService;

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask (@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> getTaskById(@PathVariable (value = "id") Long id){
        return taskService.findTaskById(id);
    }

    @PutMapping ("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> getTaskById(@PathVariable (value = "id") Long id, @RequestBody Task task){
        return taskService.updateTaskById(task, id);
    }

    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks(){
        return taskService.listAllTasks();
    }


    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteTaskById(@PathVariable (value = "id") Long id){
        return taskService.deleteById(id);
    }
}

