package br.com.joserprimo.ToDoList.Exception;

import org.springframework.http.ResponseEntity;

public class ValidationException extends RuntimeException{
    public ValidationException(String mensagem){
        super(mensagem);
    }
    public ValidationException(String campo, String mensagem) {
        super(String.format("Erro no campo '%s': %s", campo, mensagem));
    }
}
