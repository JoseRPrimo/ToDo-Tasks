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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper mapper;
    @InjectMocks
    private TaskService taskService;
    private TaskModel taskModel;
    private Long id;
    private TaskResponseDTO taskResponseDTO;
    private TaskCreateRequestDTO taskCreateRequestDTO;
    private TaskUpdateRequestDTO taskUpdateRequestDTO;
    private TaskPatchRequestDTO taskPatchRequestDTO;

    @BeforeEach
    void setUp(){
        id = 1L;
        taskModel = new TaskModel("Titulo", "Descricao");
        taskResponseDTO = new TaskResponseDTO(1L, "Titulo", "Descricao", TaskStatus.PENDENTE, null, null);
        taskCreateRequestDTO = new TaskCreateRequestDTO("Titulo", "Descricao");
        taskUpdateRequestDTO = new TaskUpdateRequestDTO("Titulo novo", "Descricao nova");
        taskPatchRequestDTO = new TaskPatchRequestDTO("Titulo novo", "Descricao nova");


    }


    //listarId
    @Test
    void deveListarTaskPorIdQuandoExistir() {
        //arrange

        when(mapper.toResponse(taskModel)).thenReturn(taskResponseDTO);
        when(taskRepository.findById(id)).thenReturn(Optional.of(taskModel));

        //act
        TaskResponseDTO response = taskService.listarId(id);

        //asserts
        assertNotNull(response);
        assertEquals("Titulo", response.getTitulo());
        assertEquals("Descricao", response.getDescricao());
        assertEquals("Titulo", taskModel.getTitulo());

    }

    @Test
    void naoDeveListarTaskPorIdQuandoNaoExistir() {
        // arrange
        when(taskRepository.findById(id)).thenReturn(Optional.empty());
        //act, asserts
        assertThrows(ResourceNotFoundException.class, () -> taskService.listarId(id));
    }

    //criar
    @Test
    void deveCriarTaskQuandoNaoExisteOutraPendenteComMesmoTitulo() {
        //arrange
        when(taskRepository.existsByTituloIgnoreCaseAndTaskStatus("Titulo", TaskStatus.PENDENTE)).thenReturn(false);
        when(mapper.toEntity(taskCreateRequestDTO)).thenReturn(taskModel);
        when(mapper.toResponse(taskModel)).thenReturn(taskResponseDTO);
        when(taskRepository.save(taskModel)).thenReturn(taskModel);

        //act
        TaskResponseDTO response = taskService.criar(taskCreateRequestDTO);

        //asserts
        assertNotNull(response);
        assertEquals("Titulo", response.getTitulo());
        assertEquals("Titulo", taskModel.getTitulo());

    }

    @Test
    void naoDeveCriarTaskComTituloDuplicadoPendente() {
        //arrange
        when(taskRepository.existsByTituloIgnoreCaseAndTaskStatus("Titulo", TaskStatus.PENDENTE)).thenReturn(true);
        //act, asserts
        assertThrows(BusinessRuleException.class, () -> taskService.criar(taskCreateRequestDTO));
    }

    //delete
    @Test
    void deveDeletarTaskQuandoExistir() {
        //arrange

        when(taskRepository.existsById(id)).thenReturn(true);
        //act
        taskService.delete(id);
        //asserts
        verify(taskRepository).deleteById(id);
    }

    @Test
    void naoDeveDeletarTaskQuandoNaoExistir() {
        //arramge

        when(taskRepository.existsById(id)).thenReturn(false);
        //act,asserts
        assertThrows(ResourceNotFoundException.class, () -> taskService.delete(id));
        verify(taskRepository, never()).deleteById(any());
    }

    //concluir
    @Test
    void deveConcluirUmaTaskPorId() {
        //arrange
        TaskResponseDTO taskResponseDTO1 = new TaskResponseDTO(1L, "Titulo", "Descricao", TaskStatus.CONCLUIDA, null, null);
        when(taskRepository.findById(id)).thenReturn(Optional.of(taskModel));

        when(mapper.toResponse(taskModel)).thenReturn(taskResponseDTO1);
        //act
        TaskResponseDTO response = taskService.concluir(id);
        //asserts
        assertNotNull(response);
        assertEquals("Titulo", response.getTitulo());
        assertEquals("Descricao", response.getDescricao());
        assertEquals(TaskStatus.CONCLUIDA, response.getTaskStatus());
        assertEquals(TaskStatus.CONCLUIDA, taskModel.getTaskStatus());

        verify(mapper).toResponse(taskModel);
    }

    @Test
    void naoDeveConcluirUmaTaskInexistente() {
        //arrange
        when(taskRepository.findById(id)).thenReturn(Optional.empty());
        //act,asserts
        assertThrows(ResourceNotFoundException.class, () -> taskService.concluir(id));
    }

    @Test
    void naoDeveConcluirUmaTaskJaConcluida() {
        //arrange
        when(taskRepository.findById(id)).thenReturn(Optional.of(taskModel));
        //act
        taskService.concluir(id);
        //asserts
        assertThrows(BusinessRuleException.class, () -> taskService.concluir(id));

    }

    //atualizar
    @Test
    void deveAtualizarTaskExistentePorId() {
        //arrange
        TaskResponseDTO taskResponseDTO1 = new TaskResponseDTO(1L, "Titulo novo", "Descricao nova", TaskStatus.PENDENTE, null, null);
        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(taskModel));
        when(mapper.toResponse(taskModel)).thenReturn(taskResponseDTO1);

        //act
        TaskResponseDTO response = taskService.atualizar(id, taskUpdateRequestDTO);

        //asserts
        assertNotNull(response);
        assertEquals("Titulo novo", response.getTitulo());
        verify(mapper).updateEntity(taskModel, taskUpdateRequestDTO);
        verify(mapper).toResponse(taskModel);

    }

    @Test
    void naoDeveAtualizarTaskInexistente(){
        //arrange
        when(taskRepository.findById(id)).thenReturn(Optional.empty());
        //act,asserts
        assertThrows(ResourceNotFoundException.class,()-> taskService.atualizar(id, taskUpdateRequestDTO));
    }

    @Test
    void naoDeveAtualizarTaskConcluida(){
        //arrange
        when(taskRepository.findById(id)).thenReturn(Optional.of(taskModel));
        taskModel.concluir();
        //act,asserts

        assertThrows(BusinessRuleException.class, ()->taskService.atualizar(id, taskUpdateRequestDTO));
    }

    //alterarParcial

    @Test
    void deveAlterarParcialmenteTaskExistente(){
        //arrange
        TaskResponseDTO taskResponseDTO1 = new TaskResponseDTO(1L, "Titulo novo", "Descricao", TaskStatus.PENDENTE, null, null);
        when(taskRepository.findById(id)).thenReturn(Optional.of(taskModel));
        when(mapper.toResponse(taskModel)).thenReturn(taskResponseDTO1);

        //act
        TaskResponseDTO response = taskService.alterarParcial(taskPatchRequestDTO, id);
        //asserts
        assertNotNull(response);
        assertEquals("Titulo novo", response.getTitulo());
        verify(mapper).patchEntity(taskModel, taskPatchRequestDTO);
        verify(mapper).toResponse(taskModel);


    }
    @Test
    void naoDeveAlterarParcialmenteTaskInexistente(){
        //arrange
        when(taskRepository.findById(id)).thenReturn(Optional.empty());
        //act,asserts
        assertThrows(ResourceNotFoundException.class, ()->taskService.alterarParcial(taskPatchRequestDTO, id));
    }

    @Test
    void naoDeveAlterarTaskConcluidaParcialmente(){
        //arrange
        when(taskRepository.findById(id)).thenReturn(Optional.of(taskModel));
        taskModel.concluir();
        //act,asserts
        assertThrows(BusinessRuleException.class, ()-> taskService.alterarParcial(taskPatchRequestDTO, id));
    }

    @Test
    void deveListarTasksComPaginacao() {
        // arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<TaskModel> pageModel =new PageImpl<>(List.of(taskModel), pageable, 1);
        when(taskRepository.findAll(pageable)).thenReturn(pageModel);
        when(mapper.toResponse(taskModel)).thenReturn(taskResponseDTO);
        // act
        Page<TaskResponseDTO> response = taskService.listar(pageable);
        // assert
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals("Titulo", response.getContent().get(0).getTitulo());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoExistirTasks() {
        //arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<TaskModel> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(taskRepository.findAll(pageable)).thenReturn(emptyPage);
        //act
        Page<TaskResponseDTO> response = taskService.listar(pageable);
        //asserts
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

}
