package br.com.joserprimo.ToDoList.Service;

import br.com.joserprimo.ToDoList.DTO.TaskMapper;
import br.com.joserprimo.ToDoList.DTO.request.TaskCreateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskPatchRequestDTO;
import br.com.joserprimo.ToDoList.DTO.request.TaskUpdateRequestDTO;
import br.com.joserprimo.ToDoList.DTO.response.TaskResponseDTO;
import br.com.joserprimo.ToDoList.Exception.BusinessRuleException;
import br.com.joserprimo.ToDoList.Exception.ResourceNotFoundException;
import br.com.joserprimo.ToDoList.Model.TaskModel;
import br.com.joserprimo.ToDoList.Model.TaskStatus;
import br.com.joserprimo.ToDoList.Repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


@Transactional
@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskMapper mapper;

    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> listar(Pageable pageable){
        return taskRepository.findAll(pageable).map(mapper::toResponse);


//        SpringDataWebProperties.Sort sort =
//       return taskRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO listarId(Long id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Task não encontrada com id: " + id));
        return mapper.toResponse(task);
    }

    public TaskResponseDTO criar(TaskCreateRequestDTO dto){
        String titulo = dto.getTitulo();
        if(taskRepository.existsByTituloIgnoreCaseAndTaskStatus(titulo, TaskStatus.PENDENTE)){
            throw new BusinessRuleException("Já existe task pendente com esse titulo.");
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
        task.validarAlteracao();
        mapper.patchEntity(task, dto);
        return mapper.toResponse(task);
    }

    public TaskResponseDTO atualizar(Long id, TaskUpdateRequestDTO dto) {
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task nao encontrada com id: " + id));
        task.validarAlteracao();
        mapper.updateEntity(task, dto);
        return mapper.toResponse(task);
    }

    public TaskResponseDTO concluir(Long id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task nao encontrada com id: "+id));

      task.concluir();
      return mapper.toResponse(task);
    }



}
