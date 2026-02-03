package br.com.joserprimo.ToDoList.DTO.request;

import br.com.joserprimo.ToDoList.Model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskCreateRequestDTO {

    @NotBlank(message = "O titulo é obrigatório")
    @Size(min = 3, max = 100, message = "Titulo precisa ter entre 3 e 100 caracteres.")
    private String titulo;
    private String descricao;
    private TaskStatus taskStatus;
}
