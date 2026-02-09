package br.com.joserprimo.ToDoList.DTO;

import br.com.joserprimo.ToDoList.DTO.request.TaskCreateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskPatchRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskUpdateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.response.TaskResponseDTO;
import br.com.joserprimo.ToDoList.Model.TaskModel;
import org.springframework.stereotype.Component;


@Component
public class TaskMapper {

public TaskResponseDTO toResponse(TaskModel taskModel){
    return new TaskResponseDTO(
            taskModel.getId(),
            taskModel.getTitulo(),
            taskModel.getDescricao(),
            taskModel.getTaskStatus(),
            taskModel.getDataCriacao(),
            taskModel.getDataConclusao());
}

public TaskModel toEntity(TaskCreateRequestDTO taskCreateRequestDTO){
    TaskModel taskModel = new TaskModel(taskCreateRequestDTO.getTitulo(), taskCreateRequestDTO.getDescricao());
    return taskModel;
}

public void updateEntity(TaskModel task, TaskUpdateRequestDTO dto){
    task.alterarDados(dto.getTitulo(), dto.getDescricao());

}

public void patchEntity(TaskModel task, TaskPatchRequestDTO dto){
    task.alterarDados(dto.getTitulo(), dto.getDescricao());
}

}
