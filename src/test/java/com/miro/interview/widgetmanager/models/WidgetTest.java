package com.miro.interview.widgetmanager.models;

import java.time.ZonedDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class WidgetTest {

  @Test
  void incrementZ() {
    Widget widget = new Widget(1l, 100, 150, 2, 30, 50, ZonedDateTime.now());
    widget.incrementZ();

    assertEquals(3, widget.getZ());
  }

  @Test
  void update() {
    Widget widget = new Widget(1l, 100, 150, 2, 30, 50, ZonedDateTime.now());
    Widget widgetForUpdate = new Widget(2l, 70, 80, 3, 10, 30, ZonedDateTime.now());
    widgetForUpdate.update(widget);

    assertNotEquals(widget.getId(), widgetForUpdate.getId());
    assertEquals(2l, widgetForUpdate.getId());
    assertEquals(widget.getX(), widgetForUpdate.getX());
    assertEquals(widget.getY(), widgetForUpdate.getY());
    assertEquals(widget.getZ(), widgetForUpdate.getZ());
    assertEquals(widget.getWidth(), widgetForUpdate.getWidth());
    assertEquals(widget.getHeight(), widgetForUpdate.getHeight());
    assertTrue(widgetForUpdate.getLastModificationDate().isAfter(widget.getLastModificationDate()));
  }
}