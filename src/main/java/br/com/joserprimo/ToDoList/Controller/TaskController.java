package br.com.joserprimo.ToDoList.Controller;

import br.com.joserprimo.ToDoList.DTO.request.TaskCreateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskPatchRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskUpdateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.response.TaskResponseDTO;
import br.com.joserprimo.ToDoList.Model.TaskModel;
import br.com.joserprimo.ToDoList.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public List<TaskResponseDTO> listar(){
        return taskService.listar();
    }

    @GetMapping("/{id}")
    public TaskResponseDTO listarId(@PathVariable Long id){
        return taskService.findById(id);
    }

    @PostMapping
    public TaskResponseDTO criar(@RequestBody TaskCreateRequestDTO dto){
        return taskService.criar(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }

    @PatchMapping("/{id}")
    public TaskResponseDTO patch(@RequestBody TaskPatchRequestDTO dto, @PathVariable Long id){
        return taskService.alterarParcial(dto, id);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO atualizar(@RequestBody TaskUpdateRequestDTO dto, @PathVariable Long id){
        return taskService.atualizar(id, dto);
    }

}
