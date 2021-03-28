package com.miro.interview.widgetmanager.models.exceptions;

public class WidgetNotFoundException extends Exception{
  public WidgetNotFoundException() {
    super("widget not found");
  }
}
