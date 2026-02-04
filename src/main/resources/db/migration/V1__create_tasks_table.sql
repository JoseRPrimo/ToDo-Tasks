CREATE TABLE tasks_tb (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          titulo VARCHAR(255),
                          descricao VARCHAR(500),
                          task_status VARCHAR(20) NOT NULL,
                          data_criacao DATE NOT NULL
);