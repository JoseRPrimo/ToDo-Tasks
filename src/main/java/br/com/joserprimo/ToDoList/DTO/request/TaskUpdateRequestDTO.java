package br.com.joserprimo.ToDoList.DTO.request;

import br.com.joserprimo.ToDoList.Model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para atualização de uma task")
public class TaskUpdateRequestDTO {
    @NotBlank(message = "O titulo é obrigatório")
    @Size(min = 3, max = 100, message = "Titulo precisa ter entre 3 e 100 caracteres.")
    @Schema(description = "Titulo da task", example = "Estudar Spring Boot", required = true)
    private String titulo;
    @Schema(description = "Descrição da task", example = "Criar testes unitários", required = true)
    private String descricao;

}
