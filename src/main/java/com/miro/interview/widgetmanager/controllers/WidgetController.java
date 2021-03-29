package com.miro.interview.widgetmanager.controllers;


import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.services.WidgetService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = {"/widgets"},
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class WidgetController {

  private static final String DEFAULT_PAGE_SIZE = "10";

  private final WidgetService widgetService;

  @Autowired
  public WidgetController(WidgetService widgetService) {
    this.widgetService = widgetService;
  }

  @GetMapping("/{id}")
  public Widget findById(@PathVariable("id") Long id) throws WidgetNotFoundException {
    return widgetService.findById(id);
  }

  @GetMapping()
  public Collection<Widget> getAll(@RequestParam Integer pageNumber,
                                   @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
    return widgetService.findAll(pageNumber, pageSize);
  }




  @PostMapping
  @ResponseBody
  public Widget create(@RequestBody Widget widget) throws WidgetNotFoundException {
    return widgetService.save(widget);
  }

  @PutMapping
  public Widget update(@RequestBody Widget widget) throws WidgetNotFoundException {
    return widgetService.save(widget);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    widgetService.delete(id);
  }


}
