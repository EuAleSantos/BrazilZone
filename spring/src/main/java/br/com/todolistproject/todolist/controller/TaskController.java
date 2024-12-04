package br.com.todolistproject.todolist.controller;

import br.com.todolistproject.todolist.service.S3Service;
import br.com.todolistproject.todolist.service.TaskService;
import br.com.todolistproject.todolist.model.Task;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    // Criação do logger
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final S3Service s3Service;
    
    @Autowired
    private TaskService taskService;

    @Autowired
    public TaskController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    // Criar uma nova tarefa sem upload de arquivo
    @Operation(summary = "Criar nova tarefa sem upload de arquivo")
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
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

    // Atualizar tarefa
    @Operation(summary = "Atualizar tarefa por ID")
    @PutMapping("/tasks/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody Task task) {
        ResponseEntity<Task> response = taskService.updateTaskById(task, id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Tarefa atualizada com sucesso!");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Erro ao atualizar a tarefa.");
        }
    }

    // Deletar tarefa
    @Operation(summary = "Deletar tarefa por ID")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        ResponseEntity<Object> response = taskService.deleteById(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Tarefa deletada com sucesso!");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Erro ao deletar a tarefa.");
        }
    }

    // Obter todas as tarefas
    @Operation(summary = "Lista as Tarefas")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.listAllTasks());
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


    // Endpoint para upload de imagem
    @Operation(summary = "Salvar a Imagem no s3 mediante a tareda por ID")
    @PostMapping(value = "/tasks/{id}/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImageForTask(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // Verifique se a tarefa existe
            Optional<Task> taskOptional = taskService.findTaskById(id);
            if (taskOptional.isEmpty()) {
                return new ResponseEntity<>("Tarefa não encontrada", HttpStatus.NOT_FOUND);
            }

            // Faça o upload da imagem para o S3
            String url = s3Service.uploadToS3(file.getBytes(), file.getOriginalFilename());

            // Atualize a tarefa com a URL da imagem
            Task task = taskOptional.get();
            task.setFileUrl(url);
            taskService.updateTask(task, id);

            return new ResponseEntity<>("Upload realizado com sucesso. URL: " + url, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Falha no upload", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}