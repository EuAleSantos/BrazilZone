package br.com.todolistproject.todolist.service;

import br.com.todolistproject.todolist.model.Task;
import br.com.todolistproject.todolist.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void updateTask(Task task, Long id) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            taskRepository.save(task);
        }
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    // Renomeie o novo método para evitar a duplicata
    public ResponseEntity<Task> findById(Long id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    // Corrigido: ResponseEntity com .ok().body()
    public ResponseEntity<Task> updateTaskById(Task task, Long id) {
        return taskRepository.findById(id)
                .map(taskToUpdate -> {
                    taskToUpdate.setTitle(task.getTitle());
                    taskToUpdate.setDescription(task.getDescription());
                    taskToUpdate.setDeadLine(task.getDeadLine());
                    Task updated = taskRepository.save(taskToUpdate);
                    return ResponseEntity.ok().body(updated); // Usar body() para passar a resposta
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Corrigido: ResponseEntity com .ok().body()
    public ResponseEntity<Object> deleteById(Long id) {
        return taskRepository.findById(id)
                .map(taskToDelete -> {
                    taskRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
}
