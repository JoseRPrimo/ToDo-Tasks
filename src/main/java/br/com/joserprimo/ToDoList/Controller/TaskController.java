package br.com.joserprimo.ToDoList.Controller;
import br.com.joserprimo.ToDoList.DTO.request.TaskCreateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskPatchRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskUpdateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.response.TaskResponseDTO;
import br.com.joserprimo.ToDoList.Service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@Tag(name = "Tasks", description = "Endpoints para gerenciamento de tarefas")
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Page<TaskResponseDTO> listar(@PageableDefault(size=10)
                                        @SortDefault.SortDefaults({@SortDefault( sort = "taskStatus", direction = Sort.Direction.DESC),
                                                                    @SortDefault(sort = "dataCriacao", direction = Sort.Direction.ASC),
                                                                   @SortDefault(sort = "id", direction = Sort.Direction.ASC)}) Pageable pageable)
    {
        return taskService.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar task por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Task não encontrada")
    })
    public TaskResponseDTO listarId(@PathVariable Long id){
        return taskService.listarId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação")
    })
    public TaskResponseDTO criar(@Valid @RequestBody TaskCreateRequestDTO dto){
        return taskService.criar(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir task por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Task não encontrada")
    })
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar task parcialmente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task atualizada(parcialmente) com sucesso"),
            @ApiResponse(responseCode = "404", description = "Task não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização da task")
    })
    public TaskResponseDTO patch(@Valid @RequestBody TaskPatchRequestDTO dto, @PathVariable Long id){
        return taskService.alterarParcial(dto, id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar task por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Task não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização da task")
    })
    public TaskResponseDTO atualizar(@Valid @RequestBody TaskUpdateRequestDTO dto, @PathVariable Long id){
        return taskService.atualizar(id, dto);
    }

    @Operation(summary = "Concluir task por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task concluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Task não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro ao alterar estado da task")
    })
    @PatchMapping("/{id}/concluir")
    public TaskResponseDTO concluir(@PathVariable Long id){
        return taskService.concluir(id);
    }

}
