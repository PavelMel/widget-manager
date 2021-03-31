package com.miro.interview.widgetmanager.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * Error representation
 */
public class ErrorResponse {
  private String message;
}
