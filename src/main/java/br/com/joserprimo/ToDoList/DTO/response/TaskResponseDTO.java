package br.com.joserprimo.ToDoList.DTO.response;

import br.com.joserprimo.ToDoList.Model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO com os dados de resposta de uma task")
public class TaskResponseDTO {
    @Schema(description = "ID único da task", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Título da task", example = "Estudar Spring Boot")
    private String titulo;
    @Schema(description = "Descrição detalhada da task", example = "Criar testes unitários")
    private String descricao;
    @Schema(description = "Status atual da task", example = "PENDENTE", allowableValues = {"PENDENTE", "CONCLUIDA"})
    private TaskStatus taskStatus;
    @Schema(description = "Data de criação da task", example = "2024-01-15")
    private LocalDate data_criacao;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Data de conclusão da task (só aparece se estiver concluída)", example = "2024-01-20", nullable = true)
    private LocalDate dataConclusao;

}
