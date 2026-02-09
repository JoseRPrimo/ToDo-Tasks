package br.com.joserprimo.ToDoList.Model;

import br.com.joserprimo.ToDoList.Exception.BusinessRuleException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descricao")
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus = TaskStatus.PENDENTE;
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDate dataCriacao;
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDate.now();
    }
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

//Construtor
    public TaskModel(String titulo, String descricao){
        if(titulo==null || titulo.isBlank()){
            throw new BusinessRuleException("Titulo da task é obrigatório");
        }
        this.titulo = titulo;
        this.descricao = descricao;
        this.taskStatus = TaskStatus.PENDENTE;
    }

//    REGRAS DE DOMINIO
    public void concluir() {
        if (this.taskStatus != TaskStatus.PENDENTE) {
            throw new BusinessRuleException(
                    "Você só pode concluir uma task pendente!"
            );
        }
        this.taskStatus = TaskStatus.CONCLUIDA;
        this.dataConclusao = LocalDate.now();
    }

    public void validarAlteracao(){
        if(this.getTaskStatus()==TaskStatus.CONCLUIDA){
            throw new BusinessRuleException("Uma task já concluida não pode sofrer alterações");
        }
    }

    public void alterarDados(String titulo, String descricao){
        validarAlteracao();
        if(titulo!=null){
        this.titulo = titulo;
        }
        if(descricao!=null){
        this.descricao = descricao;
        }
    }

}
