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
    TaskModel taskModel = new TaskModel();
    taskModel.setTitulo(taskCreateRequestDTO.getTitulo());
    taskModel.setDescricao(taskCreateRequestDTO.getDescricao());
    return taskModel;
}

public void updateEntity(TaskModel taskModel, TaskUpdateRequestDTO taskUpdateRequestDTO){
    taskModel.setTitulo(taskUpdateRequestDTO.getTitulo());
    taskModel.setDescricao(taskUpdateRequestDTO.getDescricao());
    taskModel.setTaskStatus(taskUpdateRequestDTO.getTaskStatus());

}

public void patchEntity(TaskModel task, TaskPatchRequestDTO dto){

    if (dto.getTitulo()!=null){
        task.setTitulo(dto.getTitulo());
    }
    if (dto.getDescricao()!=null){
        task.setDescricao(dto.getDescricao());
    }
    if (dto.getTaskStatus()!=null){
        task.setTaskStatus(dto.getTaskStatus());
    }
}

}
