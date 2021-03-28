package com.miro.interview.widgetmanager.services;

import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.repositories.IWidgetRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class WidgetService {

  private final IWidgetRepository widgetRepository;

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

  public Widget save(@NonNull Widget widget) throws WidgetNotFoundException {
    return widgetRepository.save(widget);
  }

  public void delete(@NonNull Long id){
    widgetRepository.delete(id);
  }



}
