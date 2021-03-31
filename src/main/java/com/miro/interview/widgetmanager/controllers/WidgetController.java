package com.miro.interview.widgetmanager.controllers;


import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.services.WidgetService;
import java.util.Collection;
import javax.validation.Valid;
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
/**
 * Requests handler for CRUD operation with Widgets
 */
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
  public Collection<Widget> getAll() {
    return widgetService.findAll();
  }

  @GetMapping("/page")
  public Collection<Widget> getAllByPage(@RequestParam Integer pageNumber,
                                         @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
    return widgetService.findAllWithPagination(pageNumber, pageSize);
  }


  @GetMapping("/dashboard")
  public Collection<Widget> getAllForDashboard(@RequestParam Integer x,
                                               @RequestParam Integer y,
                                               @RequestParam Integer width,
                                               @RequestParam Integer height) {
    return widgetService.findAllForDashboard(x, y, width, height);
  }


  @PostMapping
  @ResponseBody
  public Widget create(@Valid @RequestBody Widget widget) throws WidgetNotFoundException {
    return widgetService.save(widget);
  }

  @PutMapping
  public Widget update(@Valid @RequestBody Widget widget) throws WidgetNotFoundException {
    return widgetService.save(widget);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    widgetService.delete(id);
  }


}
