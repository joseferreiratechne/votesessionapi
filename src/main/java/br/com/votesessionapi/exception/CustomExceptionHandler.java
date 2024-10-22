package br.com.votesessionapi.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(SessionClosedException.class)
    public ResponseEntity<Object> handleSessionClosedException(SessionClosedException ex) {
        return new ResponseEntity<>(new ErrorResponse("SESSION_CLOSED", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyVotedException.class)
    public ResponseEntity<Object> handleAlreadyVotedException(AlreadyVotedException ex) {
        return new ResponseEntity<>(new ErrorResponse("ALREADY_VOTED", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MemberAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleMemberAlreadyRegisteredException(MemberAlreadyRegisteredException ex) {
        return new ResponseEntity<>(new ErrorResponse("MEMBER_ALREADY_REGISTERED", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid input: " + ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse("INVALID_INPUT", errorMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VotingNotAllowedException.class)
    public ResponseEntity<Object> handleVotingNotAllowedException(VotingNotAllowedException ex) {
        return new ResponseEntity<>(new ErrorResponse("VOTING_NOT_ALLOWED", ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse("NOT_FOUND", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(IllegalStateException ex) {
        return new ResponseEntity<>(new ErrorResponse("SESSION_ALREADY_OPEN", ex.getMessage()), HttpStatus.CONFLICT);
    }

    // Classe interna para estruturar a resposta de erro
    static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        // Getters e setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
