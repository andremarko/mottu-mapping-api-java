package com.mottu.mapping.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ModelNotFoundException.class)
  public ResponseEntity<String> handleModelNotFound(ModelNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(SectorNotFoundException.class)
  public ResponseEntity<String> handleSectorNotFound(SectorNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(MotoNotFoundException.class)
  public ResponseEntity<String> handleMotoNotFound(MotoNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(MotoYardNotFoundException.class)
  public ResponseEntity<String> handleMotoYardNotFound(MotoYardNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
