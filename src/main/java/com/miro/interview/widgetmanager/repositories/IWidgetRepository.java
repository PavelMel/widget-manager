package com.miro.interview.widgetmanager.repositories;

import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;


public interface IWidgetRepository {

  Optional<Widget> findById(@NonNull Long id);

  Optional<Widget> findByZIndex(@NonNull Integer z);

  List<Widget> findAll();

  Widget save(@NonNull Widget widget) throws WidgetNotFoundException;

  void delete(@NonNull Long id);

  void deleteAll();
}