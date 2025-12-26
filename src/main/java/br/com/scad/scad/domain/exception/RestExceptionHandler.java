package br.com.scad.scad.domain.exception;

import br.com.scad.scad.generated.model.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<ErrorResponse> handleAuthorException(AuthorException ex) {
        log.error("AuthorException: "+ ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode(ex.getClass().getSimpleName());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        //todo  Extrai APENAS a mensagem de cada violação e as une em uma única string.
        log.error("ConstraintViolationException:  " + ex.getMessage());
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(errorMessage);
        errorResponse.setCode("ValidationError");
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex) {
        log.error("Exception:  "+  ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("An unexpected error occurred: " + ex.getMessage());
        errorResponse.setCode("InternalServerError");
        return errorResponse;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        log.error("UsernameNotFoundException:  " + usernameNotFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(usernameNotFoundException.getMessage());
        errorResponse.setCode("UsernameNotFoundException");
        return errorResponse;

    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException acessDeniedException){
        log.error("AccessDeniedException:  "+ acessDeniedException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(acessDeniedException.getMessage());
        errorResponse.setCode("AccessDeniedException");
        return errorResponse;

    }
}