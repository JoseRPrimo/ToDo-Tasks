package br.com.joserprimo.ToDoList.DTO.request;

import br.com.joserprimo.ToDoList.Model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPatchRequestDTO {

    private String titulo;
    private String descricao;
    private TaskStatus taskStatus;
}
