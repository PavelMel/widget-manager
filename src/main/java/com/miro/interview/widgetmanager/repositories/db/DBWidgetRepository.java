package com.miro.interview.widgetmanager.repositories.db;

import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.repositories.IWidgetRepository;
import com.miro.interview.widgetmanager.utils.StreamUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Profile("db")
public class DBWidgetRepository implements IWidgetRepository {

  private final CRUDWidgetRepository widgetRepository;

  @Autowired
  public DBWidgetRepository(CRUDWidgetRepository widgetRepository) {
    this.widgetRepository = widgetRepository;
  }

  @Override
  public Optional<Widget> findById(Long id) {
    return widgetRepository.findById(id);
  }

  @Override
  public Optional<Widget> findByZIndex(Integer z) {
    return widgetRepository.findByZ(z);
  }

  @Override
  public List<Widget> findAll() {
    return StreamUtils.toList(widgetRepository.findAll());
  }

  @Transactional
  @Override
  public Widget save(Widget widget) throws WidgetNotFoundException {
    if (widget.getId() != null){
      return update(widget);
    }else{
      return add(widget);
    }
  }

  @Transactional
  protected Widget add(Widget widget){
    shiftZIndex(widget);
    return widgetRepository.save(widget);
  }

  @Transactional
  protected Widget update(Widget widget) throws WidgetNotFoundException {
    Optional<Widget> optionalSavedWidget = widgetRepository.findById(widget.getId());
    Widget savedWidget = optionalSavedWidget.orElseThrow(WidgetNotFoundException::new);
    shiftZIndex(widget);
    savedWidget.update(widget);
    return widgetRepository.save(savedWidget);
  }

  @Transactional
  protected void shiftZIndex(Widget widget){
    Optional<Widget> optionalExistsWidgetWithZIndex = widgetRepository.findByZ(widget.getZ());
    if (optionalExistsWidgetWithZIndex.isPresent()){
      List<Widget> widgetsToShiftZIndex = widgetRepository.findAllByZGreaterThanEqual(widget.getZ());
      Integer previousZIndex = widgetsToShiftZIndex.get(0).getZ();
      for (Widget widgetToShiftZIndex : widgetsToShiftZIndex){
        if (widgetToShiftZIndex.getZ() - previousZIndex >= 2 ){
          break;
        }
        widgetToShiftZIndex.incrementZ();
        widgetRepository.save(widgetToShiftZIndex);
      }
    }
  }


  @Override
  public void delete(Long id) {
    widgetRepository.deleteById(id);
  }

  @Override
  public void deleteAll() {
    widgetRepository.deleteAll();

  }

}