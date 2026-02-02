package br.com.joserprimo.ToDoList.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks_tb")
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
    private TaskStatus taskStatus;
    @Column(name = "data_criacao")
    private LocalDate data_criacao;
}
