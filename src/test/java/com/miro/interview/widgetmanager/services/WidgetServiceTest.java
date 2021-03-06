package com.miro.interview.widgetmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miro.interview.widgetmanager.models.Dashboard;
import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.repositories.IWidgetRepository;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;


@SpringBootTest
class WidgetServiceTest {

  @Autowired
  private WidgetService widgetService;

  @MockBean
  private IWidgetRepository widgetRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testPArse(){
    Widget widget = new Widget(1L, 100, 150, 2, 30, 50, ZonedDateTime.now());
    try {
      System.out.println(objectMapper.writeValueAsString(widget));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }


  }


  @Test
  void findById() throws WidgetNotFoundException {
    Long id = 1L;
    Widget widget = new Widget(id, 100, 150, 2, 30, 50, ZonedDateTime.now());
    Mockito
        .when(widgetRepository.findById(id))
        .thenReturn(Optional.of(widget));

    Widget receivedWidget = widgetService.findById(id);
    assertNotNull(receivedWidget);
    assertEquals(widget, receivedWidget);
  }

  @Test
  void findByIdWithException() {
    Long id = 1L;
    Mockito
        .when(widgetRepository.findById(id))
        .thenReturn(Optional.ofNullable(null));

    assertThrows(WidgetNotFoundException.class, () -> widgetService.findById(id));
  }

  @Test
  void findAll() {
    Widget firstWidget = new Widget(1L, 100, 150, 1, 30, 50, ZonedDateTime.now());
    Widget secondWidget = new Widget(2L, 70, 80, 2, 10, 20, ZonedDateTime.now());

    List<Widget> widgets = Arrays.asList(firstWidget, secondWidget);
    Mockito
        .when(widgetRepository.findAll())
        .thenReturn(widgets);

    List<Widget> receivedWidgets = widgetService.findAll();

    assertNotNull(receivedWidgets);
    assertEquals(2, receivedWidgets.size());
    assertEquals(widgets, receivedWidgets);
  }

  @Test
  void save() throws WidgetNotFoundException {
    Mockito
        .when(widgetRepository.save(Mockito.any(Widget.class)))
        .thenAnswer(i -> { Widget widget = (Widget)i.getArguments()[0]; widget.setId(getRandomId(10, 100)); return widget; });

    Widget widget = new Widget(null, 100, 150, 1, 30, 50, ZonedDateTime.now());
    widget = widgetService.save(widget);

    assertNotNull(widget);
    assertNotNull(widget.getId());
  }


  @Test
  void saveWithException() throws WidgetNotFoundException {
    Mockito
        .when(widgetRepository.save(any(Widget.class)))
        .thenThrow(WidgetNotFoundException.class);

    Widget widget = new Widget(50L, 100, 150, 1, 30, 50, ZonedDateTime.now());
    assertThrows(WidgetNotFoundException.class, () -> widgetService.save(widget));
  }

  @Test
  void delete() {
    Long id = 1L;

    widgetService.delete(id);
    verify(widgetRepository, times(1)).delete(any(Long.class));
  }

  private long getRandomId(int low, int high){
    Random r = new Random();
    return r.nextInt(high-low) + low;
  }

  @Test
  void testFindAllWithPropagation() {
    Widget firstWidget = new Widget(1L, 100, 150, 1, 30, 50, ZonedDateTime.now());
    Widget secondWidget = new Widget(2L, 70, 80, 2, 10, 20, ZonedDateTime.now());

    List<Widget> widgets = Arrays.asList(firstWidget, secondWidget);

    Mockito
        .when(widgetRepository.findAll(any(Pageable.class)))
        .thenReturn(widgets);

    Integer pageNumber = 0;
    Integer pageSize = 2;

    List<Widget> receivedWidgets = widgetService.findAllWithPagination(pageNumber, pageSize);

    assertNotNull(receivedWidgets);
    assertEquals(2, receivedWidgets.size());
    assertEquals(widgets, receivedWidgets);
  }

  @Test
  void testFindAllForDashboard() {
    Widget firstWidget = new Widget(1L, 100, 150, 1, 30, 50, ZonedDateTime.now());
    Widget secondWidget = new Widget(2L, 70, 80, 2, 10, 20, ZonedDateTime.now());
    Dashboard dashboard = new Dashboard(10, 100, 90, 85);

    List<Widget> widgets = Arrays.asList(firstWidget, secondWidget);

    Mockito
        .when(widgetRepository.findAll(any(Dashboard.class)))
        .thenReturn(widgets);

    List<Widget> receivedWidgets = widgetService.findAllForDashboard(dashboard.getX(), dashboard.getY(), dashboard.getWidth(), dashboard.getHeight());

    assertNotNull(receivedWidgets);
    assertEquals(2, receivedWidgets.size());
    assertEquals(widgets, receivedWidgets);
  }

  @Test
  void testFindAllWithPropagationIncorrectPageNumber() {
    Integer pageNumber = -1;
    Integer pageSize = 2;
    assertThrows(IllegalArgumentException.class, () -> widgetService.findAllWithPagination(pageNumber, pageSize));
  }

  @Test
  void testFindAllWithPropagationIncorrectPageSize() {
    Integer pageNumber = 1;
    Integer pageSize = 2000;
    assertThrows(IllegalArgumentException.class, () -> widgetService.findAllWithPagination(pageNumber, pageSize));
  }
}