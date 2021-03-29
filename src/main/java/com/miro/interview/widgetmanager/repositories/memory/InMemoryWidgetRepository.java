package com.miro.interview.widgetmanager.repositories.memory;

import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.repositories.IWidgetRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@Profile("memory")
public class InMemoryWidgetRepository implements IWidgetRepository {

  private final Map<Long, Widget> widgetStorage;
  private final NavigableMap<Integer, Widget> zIndexStorage;

  private final Lock readLock;
  private final Lock writeLock;

  private Long maxWidgetId;

  public InMemoryWidgetRepository() {
    this.widgetStorage = new HashMap();
    this.zIndexStorage = new TreeMap<>();

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    this.readLock = readWriteLock.readLock();
    this.writeLock = readWriteLock.writeLock();

    this.maxWidgetId = 0l;
  }


  @Override
  public Optional<Widget> findById(@NonNull Long id) {
    this.readLock.lock();
    try {
      return Optional.ofNullable(this.widgetStorage.get(id));
    } finally {
      this.readLock.unlock();
    }
  }

  @Override
  public Optional<Widget> findByZIndex(@NonNull Integer z) {
    this.readLock.lock();
    try {
      return Optional.ofNullable(this.zIndexStorage.get(z));
    } finally {
      this.readLock.unlock();
    }
  }

  @Override
  public List<Widget> findAll() {
    readLock.lock();
    try {
      return new ArrayList<>(zIndexStorage.values());
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public List<Widget> findAll(Pageable pageable) {
    return zIndexStorage.values().stream()
                                  .skip(pageable.getOffset())
                                  .limit(pageable.getPageSize())
                                .collect(Collectors.toList());
  }

  @Override
  public Widget save(@NonNull Widget widget) throws WidgetNotFoundException {
    writeLock.lock();
    try {
      if (widget.getId() != null){
        return update(widget);
      }else{
        return add(widget);
      }
    } finally {
      writeLock.unlock();
    }
  }

  private Widget add(@NonNull Widget widget){
    widget.setId(++maxWidgetId);
    widget.setLastModificationDate(ZonedDateTime.now());
    shiftZIndex(widget);
    updateStorages(widget);
    return widget;
  }

  private Widget update(@NonNull Widget widget) throws WidgetNotFoundException {
    Widget savedWidget = widgetStorage.get(widget.getId());
    if (savedWidget == null){
      throw new WidgetNotFoundException();
    }
    savedWidget.update(widget);
    shiftZIndex(savedWidget);
    updateStorages(savedWidget);
    return savedWidget;
  }

  private void shiftZIndex(Widget widget){
    Widget existsWidgetWithZIndex = zIndexStorage.get(widget.getZ());
    if (existsWidgetWithZIndex != null){

      List<Widget> widgetsToShiftZIndex = new ArrayList<>(zIndexStorage.tailMap(existsWidgetWithZIndex.getZ()).values());
      Integer previousZIndex = widgetsToShiftZIndex.get(0).getZ();
      for (Widget widgetToShiftZIndex : widgetsToShiftZIndex){
        if (widgetToShiftZIndex.getZ() - previousZIndex >= 2 ){
          break;
        }

        widgetToShiftZIndex.incrementZ();
        updateStorages(widgetToShiftZIndex);
      }
    }
  }

  private void updateStorages(Widget widget){
    widgetStorage.put(widget.getId(), widget);
    zIndexStorage.put(widget.getZ(), widget);
  }


  @Override
  public void delete(@NonNull Long id) {
    writeLock.lock();
    try {
      Widget removedWidget = widgetStorage.remove(id);
      if (removedWidget != null){
        zIndexStorage.remove(removedWidget.getZ());
      }
    } finally {
      writeLock.unlock();
    }
  }

  @Override
  public void deleteAll() {
    widgetStorage.clear();
    zIndexStorage.clear();
  }
}
