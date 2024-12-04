package br.com.todolistproject.todolist.repository;

import br.com.todolistproject.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Defina métodos personalizados, se necessário
}
