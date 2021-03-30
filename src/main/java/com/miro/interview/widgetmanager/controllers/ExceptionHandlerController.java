package com.miro.interview.widgetmanager.controllers;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.miro.interview.widgetmanager.controllers.models.ErrorResponse;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler({WidgetNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex, WebRequest request) {
    return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({IllegalArgumentException.class, JsonMappingException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ErrorResponse> handleIBadRequestExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
