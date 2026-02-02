package br.com.joserprimo.ToDoList.Repository;

import br.com.joserprimo.ToDoList.Model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {

}
