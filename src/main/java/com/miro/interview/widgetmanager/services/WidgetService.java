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
public class WidgetService {

  private final IWidgetRepository widgetRepository;

  @Value("${pagination.page.size.max}")
  private Integer pageMaxSize;

  @Autowired
  public WidgetService(IWidgetRepository widgetRepository) {
    this.widgetRepository = widgetRepository;
  }

  public Widget findById(@NonNull Long id) throws WidgetNotFoundException {
    return widgetRepository.findById(id).orElseThrow(WidgetNotFoundException::new);
  }

  public List<Widget> findAll(){
    return widgetRepository.findAll();
  }

  public List<Widget> findAllWithPagination(Integer pageNumber, Integer pageSize){
    if (pageNumber < 0 || pageSize <=0 || pageSize > this.pageMaxSize){
      throw new IllegalArgumentException("incorrect pagination values");
    }
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    return widgetRepository.findAll(pageable);
  }

  public List<Widget> findAllForDashboard(Integer x, Integer y, Integer width, Integer height){
    Dashboard dashboard = new Dashboard(x, y, width, height);
    return widgetRepository.findAll(dashboard);
  }



  public Widget save(@NonNull Widget widget) throws WidgetNotFoundException {
    return widgetRepository.save(widget);
  }

  public void delete(@NonNull Long id){
    widgetRepository.delete(id);
  }



}
