package br.com.joserprimo.ToDoList.DTO.response;

import br.com.joserprimo.ToDoList.Model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private TaskStatus taskStatus;
    private LocalDate data_criacao;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate dataConclusao;

}
