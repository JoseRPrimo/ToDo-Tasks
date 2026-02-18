package br.com.joserprimo.ToDoList.DTO.request;

import br.com.joserprimo.ToDoList.Model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para atualização parcial de uma task")
public class TaskPatchRequestDTO {

    @Schema(description = "Título da task", example = "Estudar Spring Boot", required = true)
    private String titulo;
    @Schema(description = "Descrição da task", example = "Criar testes unitários", required = true)
    private String descricao;
}
