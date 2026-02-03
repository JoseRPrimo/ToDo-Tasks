package br.com.joserprimo.ToDoList.Service;

import br.com.joserprimo.ToDoList.DTO.TaskMapper;
import br.com.joserprimo.ToDoList.DTO.request.TaskCreateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskPatchRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskUpdateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.response.TaskResponseDTO;
import br.com.joserprimo.ToDoList.Exception.ResourceNotFoundException;
import br.com.joserprimo.ToDoList.Exception.ValidationException;
import br.com.joserprimo.ToDoList.Model.TaskModel;
import br.com.joserprimo.ToDoList.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TaskResponseDTO findById(Long id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Task não encontrada com id: " + id));
        return mapper.toResponse(task);
    }

    public TaskResponseDTO criar(TaskCreateRequestDTO dto){
        if (dto.getTitulo()==null || dto.getTitulo().trim().isEmpty()){
            throw new ValidationException("Titulo obrigatorio");
        }
        String validacaoTitulo = dto.getTitulo().trim().replace(" ","");
        if (validacaoTitulo.length()<3 || validacaoTitulo.length()>100){
            throw new ValidationException("Titulo precisa ter entre 3 e 100 caracteres.");
        }

        TaskModel task = taskRepository.save(mapper.toEntity(dto));

        return mapper.toResponse(task);

    }

    public void delete(Long id){
        if(!taskRepository.existsById(id)){
            throw new ResourceNotFoundException("Não foi possível encontrar uma task com o ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    public TaskResponseDTO alterarParcial(TaskPatchRequestDTO dto, Long id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task nao encontrada com id: "+id));
        mapper.patchEntity(task, dto);
        TaskModel atualizado = taskRepository.save(task);
        return mapper.toResponse(atualizado);
    }

    public TaskResponseDTO atualizar(Long id, TaskUpdateRequestDTO dto) {
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task nao encontrada com id: " + id));
        mapper.updateEntity(task, dto);
        TaskModel atualizado = taskRepository.save(task);
        return mapper.toResponse(atualizado);
    }





}
