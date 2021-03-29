package com.miro.interview.widgetmanager.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miro.interview.widgetmanager.controllers.models.ErrorResponse;
import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.services.WidgetService;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class WidgetControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private WidgetService widgetService;



  @Test
  void findById() throws Exception {
    Long id = 1l;
    Widget createdWidget = new Widget(1l, 100, 150, 2, 30, 50, ZonedDateTime.now());

    Mockito
        .when(widgetService.findById(id))
        .thenReturn(createdWidget);

    MvcResult result = mvc.perform(get("/widgets/"+id))
                           .andExpect(status().isOk())
                           .andReturn();

    String content = result.getResponse().getContentAsString();
    Widget receivedWidget = objectMapper.readValue(content, Widget.class);

    assertNotNull(receivedWidget);
    assertEquals(createdWidget.getId(), receivedWidget.getId());
    assertEquals(createdWidget.getX(), receivedWidget.getX());
    assertEquals(createdWidget.getY(), receivedWidget.getY());
    assertEquals(createdWidget.getZ(), receivedWidget.getZ());
    assertEquals(createdWidget.getWidth(), receivedWidget.getWidth());
    assertEquals(createdWidget.getHeight(), receivedWidget.getHeight());
    assertNotNull(receivedWidget.getLastModificationDate());
  }

  @Test
  void getAll() throws Exception{
    Widget firstWidget = new Widget(1l, 100, 150, 1, 30, 50, ZonedDateTime.now());
    Widget secondWidget = new Widget(2l, 70, 80, 2, 10, 20, ZonedDateTime.now());
    List<Widget> widgets = Arrays.asList(firstWidget, secondWidget);

    Mockito
        .when(widgetService.findAll(any(Integer.class), any(Integer.class)))
        .thenReturn(widgets);

    MvcResult result = mvc.perform(get("/widgets")
                                       .param("pageNumber", "0")
                                       .param("pageSize", "2"))
                           .andExpect(status().isOk())
                           .andReturn();

    String content = result.getResponse().getContentAsString();
    List<Widget> receivedWidgets = objectMapper.readValue(content, new TypeReference<List<Widget>>() {});

    assertNotNull(receivedWidgets);
    assertEquals(2, receivedWidgets.size());

    Widget firstReceivedWidget = receivedWidgets.get(0);
    assertNotNull(firstReceivedWidget);
    assertNotNull(firstReceivedWidget.getId());
    assertNotNull(firstReceivedWidget.getX());
    assertNotNull(firstReceivedWidget.getY());
    assertNotNull(firstReceivedWidget.getZ());
    assertNotNull(firstReceivedWidget.getHeight());
    assertNotNull(firstReceivedWidget.getWidth());
    assertNotNull(firstReceivedWidget.getLastModificationDate());

  }

  @Test
  void create() throws Exception {
    Widget newWidget = new Widget(null, 100, 150, 2, 30, 50, null);
    Widget createdWidget = new Widget(1l, 100, 150, 2, 30, 50, ZonedDateTime.now());

    Mockito
        .when(widgetService.save( Mockito.any(Widget.class)))
        .thenReturn(createdWidget);


    MvcResult result = mvc.perform(post("/widgets")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsBytes(newWidget)))
                           .andExpect(status().isOk())
                           .andReturn();

    String content = result.getResponse().getContentAsString();
    Widget receivedWidget = objectMapper.readValue(content, Widget.class);

    assertNotNull(receivedWidget);
    assertEquals(createdWidget.getId(), receivedWidget.getId());
    assertEquals(createdWidget.getX(), receivedWidget.getX());
    assertEquals(createdWidget.getY(), receivedWidget.getY());
    assertEquals(createdWidget.getZ(), receivedWidget.getZ());
    assertEquals(createdWidget.getWidth(), receivedWidget.getWidth());
    assertEquals(createdWidget.getHeight(), receivedWidget.getHeight());
    assertNotNull(receivedWidget.getLastModificationDate());
  }

  @Test
  void createWithIncorrectModel() throws Exception {
    String request = "{\"id\":null,\"x\":100,\"y\":150,\"z\":null,\"width\":null,\"height\":50,\"lastModificationDate\":null}";

    MvcResult result = mvc.perform(post("/widgets")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(request))
                           .andExpect(status().isBadRequest())
                           .andReturn();

    String content = result.getResponse().getContentAsString();
    ErrorResponse errorResponse = objectMapper.readValue(content, ErrorResponse.class);
    assertNotNull(errorResponse);
    assertNotNull(errorResponse.getMessage());
  }



  @Test
  void update() throws Exception{
    Widget updatedWidget = new Widget(1l, 100, 150, 2, 30, 60, null);
    Widget createdWidget = new Widget(1l, 100, 150, 2, 30, 60, ZonedDateTime.now());

    Mockito
        .when(widgetService.save( Mockito.any(Widget.class)))
        .thenReturn(createdWidget);


    MvcResult result = mvc.perform(put("/widgets")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsBytes(updatedWidget)))
                           .andExpect(status().isOk())
                           .andReturn();

    String content = result.getResponse().getContentAsString();
    Widget receivedWidget = objectMapper.readValue(content, Widget.class);

    assertNotNull(receivedWidget);
    assertEquals(createdWidget.getId(), receivedWidget.getId());
    assertEquals(createdWidget.getX(), receivedWidget.getX());
    assertEquals(createdWidget.getY(), receivedWidget.getY());
    assertEquals(createdWidget.getZ(), receivedWidget.getZ());
    assertEquals(createdWidget.getWidth(), receivedWidget.getWidth());
    assertEquals(createdWidget.getHeight(), receivedWidget.getHeight());
    assertNotNull(receivedWidget.getLastModificationDate());


  }

  @Test
  void updateWithWidgetNotFoundException() throws Exception {
    Widget widget = new Widget(50l, 100, 150, 2, 30, 50, null);

    Mockito
        .when(widgetService.save(Mockito.any(Widget.class)))
        .thenThrow(WidgetNotFoundException.class);


    MvcResult result = mvc.perform(post("/widgets")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsBytes(widget)))
                           .andExpect(status().isNotFound())
                           .andReturn();

    String content = result.getResponse().getContentAsString();
    ErrorResponse errorResponse = objectMapper.readValue(content, ErrorResponse.class);
    assertNotNull(errorResponse);
  }


  @Test
  void updateWithUnexpectedException() throws Exception {
    Widget widget = new Widget(50l, 100, 150, 2, 30, 50, null);

    Mockito
        .when(widgetService.save(Mockito.any(Widget.class)))
        .thenThrow(IndexOutOfBoundsException.class);


    MvcResult result = mvc.perform(post("/widgets")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsBytes(widget)))
                           .andExpect(status().isInternalServerError())
                           .andReturn();

    String content = result.getResponse().getContentAsString();
    ErrorResponse errorResponse = objectMapper.readValue(content, ErrorResponse.class);
    assertNotNull(errorResponse);
  }

  @Test
  void deleteTest() throws Exception{
    Long id = 1l;
    mvc.perform(delete("/widgets/"+id))
                           .andExpect(status().isOk());

    verify(widgetService, times(1)).delete(any(Long.class));
  }
}