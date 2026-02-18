package br.com.joserprimo.ToDoList.DTO.request;

import br.com.joserprimo.ToDoList.Model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Dados para criação de uma task")
public class TaskCreateRequestDTO {

    @NotBlank(message = "O titulo é obrigatório")
    @Size(min = 3, max = 100, message = "Titulo precisa ter entre 3 e 100 caracteres.")
    @Schema(description = "Título da task", example = "Estudar Spring Boot", required = true)
    private String titulo;
    @Schema(description = "Descrição da task", example = "Criar testes unitários", required = true)
    private String descricao;

    /**
     * Define o título da task aplicando formatação:
     * - Remove espaços extras no início e fim (trim)
     * - Substitui múltiplos espaços internos por um único espaço
     *
     * @param titulo Título a ser formatado e armazenado
     */

    @Schema(hidden = true)
    public void setTitulo(String titulo) {
        if (titulo == null) {
            this.titulo = null;
            return;
        }

        this.titulo = titulo
                .trim()
                .replaceAll("\\s+", " ");
    }
}
