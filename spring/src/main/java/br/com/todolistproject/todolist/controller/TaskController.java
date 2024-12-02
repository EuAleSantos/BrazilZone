package br.com.todolistproject.todolist.controller;

import br.com.todolistproject.todolist.model.Task;
import br.com.todolistproject.todolist.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        // Cria a tarefa usando o serviço
        Task createdTask = taskService.createTask(task);

        // Cria o payload para a notificação
        NotificacaoRequest notificacao = new NotificacaoRequest(createdTask.getId(), createdTask.getDescription());

        // Envia a notificação para o endpoint remoto
        RestTemplate restTemplate = new RestTemplate();
        String notificacaoUrl = "http://servidor:5001/notificar";
        try {
            restTemplate.postForObject(notificacaoUrl, notificacao, Void.class);
            log.info("Notificação enviada com sucesso para o ID: {}", createdTask.getId());
        } catch (Exception e) {
            log.error("Erro ao enviar notificação: {}", e.getMessage());
        }

        return createdTask;
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

    // Classe interna para o DTO da notificação
    public static class NotificacaoRequest {
        private Long id;
        private String description;

        public NotificacaoRequest(Long id, String description) {
            this.id = id;
            this.description = description;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

