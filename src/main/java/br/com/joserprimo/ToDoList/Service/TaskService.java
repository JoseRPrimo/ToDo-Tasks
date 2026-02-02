package br.com.joserprimo.ToDoList.Service;

import br.com.joserprimo.ToDoList.DTO.TaskMapper;
import br.com.joserprimo.ToDoList.DTO.request.TaskCreateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskPatchRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskUpdateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.response.TaskResponseDTO;
import br.com.joserprimo.ToDoList.Model.TaskModel;
import br.com.joserprimo.ToDoList.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskMapper mapper;


    public List<TaskResponseDTO> listar(){
       return taskRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public TaskResponseDTO listarId(Long id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() ->  new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Task não encontrada com id: " + id));
        return mapper.toResponse(task);
    }

    public TaskResponseDTO criar(TaskCreateRequestDTO dto){
        TaskModel task = mapper.toEntity(dto);
        taskRepository.save(task);
        return mapper.toResponse(task);
    }

    public void delete(Long id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task nao encontrada com id: "+id));
        taskRepository.delete(task);
    }

    public TaskResponseDTO alterarParcial(TaskPatchRequestDTO dto, Long id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task nao encontrada com id: "+id));
        mapper.patchEntity(task, dto);
        TaskModel atualizado = taskRepository.save(task);
        return mapper.toResponse(task);
    }

    public TaskResponseDTO atualizar(Long id, TaskUpdateRequestDTO dto) {
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task nao encontrada com id: " + id));
        mapper.updateEntity(task, dto);
        TaskModel atualizado = taskRepository.save(task);
        return mapper.toResponse(task);
    }





}
