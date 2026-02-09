package br.com.joserprimo.ToDoList.Model;



import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import br.com.joserprimo.ToDoList.Exception.BusinessRuleException;
import org.junit.jupiter.api.Test;
public class TaskModelTest {

    @Test
    void deveConcluirUmaTaskPendente(){
        //DADO
        TaskModel taskModel = new TaskModel("Testa Concluir", "Testa metodo concluir");

        //AÇÃO
        taskModel.concluir();

        //ENTÃO
        assertEquals(TaskStatus.CONCLUIDA, taskModel.getTaskStatus());
        assertNotNull(taskModel.getDataConclusao());
    }

    @Test
    void naoDeveConcluirTaskConcluida(){
        //DADO
        TaskModel taskModel = new TaskModel("Testa NAO concluir", "Testa se é possivel concluir uma task já concluida");

        //AÇÃO
        taskModel.concluir();

        //ENTÃO
        assertThrows(BusinessRuleException.class, taskModel::concluir);
    }

    @Test
    void naoDevePermitirAlteracaoDeTaskConcluida(){
        TaskModel taskModel = new TaskModel("Testar validacao", "Deve permitir ou nao atualização dos dados da task");
        taskModel.concluir();
        assertThrows(BusinessRuleException.class, taskModel::validarAlteracao);
    }

    @Test
    void alterarDados(){
        TaskModel taskModel = new TaskModel("Titulo original", "Descricao original");
        taskModel.alterarDados("Novo titulo", "Nova descricao");
        assertEquals("Novo titulo", taskModel.getTitulo());
        assertEquals("Nova descricao", taskModel.getDescricao());
    }
}
