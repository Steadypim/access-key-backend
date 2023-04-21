package com.example.accesskeybackend.config;

import com.example.accesskeybackend.exception.AlreadyActivatedException;
import com.example.accesskeybackend.exception.IllegalArgumentException;
import com.example.accesskeybackend.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

    private record ExceptionResponse(List<String> errors) {
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse illegalArgument(final IllegalArgumentException ex) {
        return new ExceptionResponse(List.of(ex.getMessage()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFound(final NotFoundException ex) {
        return new ExceptionResponse(List.of(ex.getMessage()));
    }

    @ExceptionHandler(value = AlreadyActivatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse alreadyActivated(final AlreadyActivatedException ex) {
        return new ExceptionResponse(List.of(ex.getMessage()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ExceptionResponse validationException(final ConstraintViolationException ex) {
        final List<String> exceptions = ex.getConstraintViolations().stream()
                .map(RestExceptionHandler::getValidationMessage)
                .toList();

        return new ExceptionResponse(exceptions);
    }


    private static <T> String getValidationMessage(final ConstraintViolation<T> violation) {
        final String className = violation.getRootBeanClass().getSimpleName();
        final String property = violation.getPropertyPath().toString();
        final Object invalidValue = violation.getInvalidValue();
        final String message = violation.getMessage();

        return String.format(
                "Invalid param: (%s [%s]) %s",
                property, invalidValue, message
        );
    }
}
