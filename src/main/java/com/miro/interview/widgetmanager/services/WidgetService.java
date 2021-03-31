package com.miro.interview.widgetmanager.services;

import com.miro.interview.widgetmanager.models.Dashboard;
import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.repositories.IWidgetRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
/**
 * Service for process Widget CRUD requests
 */
public class WidgetService {

  private final IWidgetRepository widgetRepository;

  @Value("${pagination.page.size.max}")
  private Integer pageMaxSize;

  @Autowired
  public WidgetService(IWidgetRepository widgetRepository) {
    this.widgetRepository = widgetRepository;
  }

  /**
   * get widget by id
   * @param id unique widget id
   * @return widget model
   * @throws WidgetNotFoundException
   */
  public Widget findById(@NonNull Long id) throws WidgetNotFoundException {
    return widgetRepository.findById(id).orElseThrow(WidgetNotFoundException::new);
  }

  /**
   * find all stored widgets
   * @return list of widget
   */
  public List<Widget> findAll(){
    return widgetRepository.findAll();
  }

  /**
   * find all widgets in current page (Pagination)
   * @param pageNumber sequential number of page
   * @param pageSize size of page ( default is 10, max is 500 )
   * @return list of widget
   */
  public List<Widget> findAllWithPagination(Integer pageNumber, Integer pageSize){
    if (pageNumber < 0 || pageSize <=0 || pageSize > this.pageMaxSize){
      throw new IllegalArgumentException("incorrect pagination values");
    }
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    return widgetRepository.findAll(pageable);
  }

  /**
   * Get only the widgets that fall entirely into the region(dashboard) fall into the result.
   * @param x x coordinate of dashboard
   * @param y y coordinate of dashboard
   * @param width  width of dashboard
   * @param height height of dashboard
   * @return list of widget
   */
  public List<Widget> findAllForDashboard(Integer x, Integer y, Integer width, Integer height){
    Dashboard dashboard = new Dashboard(x, y, width, height);
    return widgetRepository.findAll(dashboard);
  }


  /**
   * create (if not exists) or update Widget
   * @param widget model of Widget
   * @return model of Widget
   * @throws WidgetNotFoundException
   */
  public Widget save(@NonNull Widget widget) throws WidgetNotFoundException {
    return widgetRepository.save(widget);
  }

  /**
   * delete Widget with given id
   * @param id unique widget id
   */
  public void delete(@NonNull Long id){
    widgetRepository.delete(id);
  }

}
