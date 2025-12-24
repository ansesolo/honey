package com.alfsoftwares.honey.api.core.application.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ProblemDetail> handleNotFound(Exception ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    problem.setDetail(ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
  }

  @ExceptionHandler({
    InvalidRequestException.class,
    MethodArgumentNotValidException.class,
    ConstraintViolationException.class,
    DataAccessException.class
  })
  public ResponseEntity<ProblemDetail> handleBadRequest(Exception ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    if (ex instanceof MethodArgumentNotValidException manve) {
      problem.setDetail("Validation error");
      problem.setProperty(
          "errors",
          manve.getBindingResult().getFieldErrors().stream()
              .collect(
                  Collectors.toMap(
                      FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage)));
    } else if (ex instanceof ConstraintViolationException cve) {
      problem.setDetail("Constraint violation");
      problem.setProperty(
          "errors",
          cve.getConstraintViolations().stream()
              .collect(
                  Collectors.toMap(
                      v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage)));
    } else {
      problem.setDetail("Unexpected error");
      problem.setProperty("errors", ex.getMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
  }
}
