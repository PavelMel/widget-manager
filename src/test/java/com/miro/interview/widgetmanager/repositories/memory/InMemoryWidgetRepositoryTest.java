package com.miro.interview.widgetmanager.repositories.memory;

import com.miro.interview.widgetmanager.models.Dashboard;
import com.miro.interview.widgetmanager.models.Widget;
import com.miro.interview.widgetmanager.models.exceptions.WidgetNotFoundException;
import com.miro.interview.widgetmanager.repositories.IWidgetRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class InMemoryWidgetRepositoryTest {

  private InMemoryWidgetRepository widgetRepository = new InMemoryWidgetRepository();

  protected Widget firstWidget = new Widget(null, 100, 150, 1, 30, 50, ZonedDateTime.now());
  protected Widget secondWidget = new Widget(null, 70, 80, 2, 10, 20, ZonedDateTime.now());
  protected Widget thirdWidget = new Widget(null, 40, 90, 3, 30, 70, ZonedDateTime.now());
  protected Widget fourthWidget = new Widget(null, 120, 200, 5, 70, 80, ZonedDateTime.now());

  public IWidgetRepository getWidgetRepository(){
    return this.widgetRepository;
  }

  public Widget getFirstWidget(){
    return this.firstWidget;
  }

  public Widget getSecondWidget(){
    return this.secondWidget;
  }

  public Widget getThirdWidget(){
    return this.thirdWidget;
  }

  public Widget getFourthWidget(){
    return this.fourthWidget;
  }

  @BeforeEach
  public void init() throws WidgetNotFoundException{
    getWidgetRepository().deleteAll();

    getWidgetRepository().save(this.firstWidget);
    getWidgetRepository().save(this.secondWidget);
    getWidgetRepository().save(this.thirdWidget);
    getWidgetRepository().save(this.fourthWidget);
  }

  @Test
  void findById() {
    Long id = 1l;
    Optional<Widget> optionalWidget = getWidgetRepository().findById(id);
    assertTrue(optionalWidget.isPresent());

    Widget widget = optionalWidget.get();
    assertEquals(id, widget.getId());
    assertEquals(getFirstWidget().getId(), widget.getId());
    assertEquals(getFirstWidget().getX(), widget.getX());
    assertEquals(getFirstWidget().getY(), widget.getY());
    assertEquals(getFirstWidget().getZ(), widget.getZ());
    assertEquals(getFirstWidget().getWidth(), widget.getWidth());
    assertEquals(getFirstWidget().getHeight(), widget.getHeight());
    assertEquals(getFirstWidget().getLastModificationDate(), widget.getLastModificationDate());
  }

  @Test
  void findByIdWithWrongId() {
    Long id = 50l;
    Optional<Widget> optionalWidget = getWidgetRepository().findById(id);
    assertFalse(optionalWidget.isPresent());
  }

  @Test
  void findByZIndex() {
    Integer z = 3;

    Optional<Widget> optionalWidget = getWidgetRepository().findByZIndex(z);
    assertTrue(optionalWidget.isPresent());

    Widget widget = optionalWidget.get();
    assertEquals(getThirdWidget().getId(), widget.getId());
    assertEquals(getThirdWidget().getX(), widget.getX());
    assertEquals(getThirdWidget().getY(), widget.getY());
    assertEquals(getThirdWidget().getZ(), widget.getZ());
    assertEquals(getThirdWidget().getWidth(), widget.getWidth());
    assertEquals(getThirdWidget().getHeight(), widget.getHeight());
    assertEquals(getThirdWidget().getLastModificationDate(), widget.getLastModificationDate());
  }

  @Test
  void findByZIndexWithWrongZ() {
    Integer z = 70;

    Optional<Widget> optionalWidget = getWidgetRepository().findByZIndex(z);
    assertFalse(optionalWidget.isPresent());
  }

  @Test
  void findAll() {
    List<Widget> widgets = getWidgetRepository().findAll();
    assertNotNull(widgets);
    assertEquals(4, widgets.size());
  }

  @Test
  void saveSimpleAddToTheEnd() throws WidgetNotFoundException {
    Widget newWidget = new Widget(null, 89, 45, 6, 50, 10, ZonedDateTime.now());
    newWidget = getWidgetRepository().save(newWidget);

    assertNotNull(newWidget);
    assertNotNull(newWidget.getId());
    assertEquals(5l, newWidget.getId());
    assertEquals(89, newWidget.getX());
    assertEquals(45, newWidget.getY());
    assertEquals(6, newWidget.getZ());
    assertEquals(50, newWidget.getWidth());
    assertEquals(10, newWidget.getHeight());

    Optional<Widget> firstWidget = getWidgetRepository().findById(getFirstWidget().getId());
    Optional<Widget>  secondWidget = getWidgetRepository().findById(getSecondWidget().getId());
    Optional<Widget>  thirdWidget = getWidgetRepository().findById(getThirdWidget().getId());
    Optional<Widget>  fourthWidget = getWidgetRepository().findById(getFourthWidget().getId());

    assertTrue(firstWidget.isPresent());
    assertTrue(secondWidget.isPresent());
    assertTrue(thirdWidget.isPresent());
    assertTrue(fourthWidget.isPresent());

    //z should stay at prev value
    assertEquals(1, firstWidget.get().getZ());
    assertEquals(2, secondWidget.get().getZ());
    assertEquals(3, thirdWidget.get().getZ());
    assertEquals(5, fourthWidget.get().getZ());
  }

  @Test
  void saveSimpleAddToFreeSlot() throws WidgetNotFoundException {
    Widget newWidget = new Widget(null, 89, 45, 4, 50, 10, ZonedDateTime.now());
    newWidget = getWidgetRepository().save(newWidget);

    assertNotNull(newWidget);
    assertNotNull(newWidget.getId());
    assertEquals(5l, newWidget.getId());
    assertEquals(89, newWidget.getX());
    assertEquals(45, newWidget.getY());
    assertEquals(4, newWidget.getZ());
    assertEquals(50, newWidget.getWidth());
    assertEquals(10, newWidget.getHeight());

    Optional<Widget> firstWidget = getWidgetRepository().findById(getFirstWidget().getId());
    Optional<Widget>  secondWidget = getWidgetRepository().findById(getSecondWidget().getId());
    Optional<Widget>  thirdWidget = getWidgetRepository().findById(getThirdWidget().getId());
    Optional<Widget>  fourthWidget = getWidgetRepository().findById(getFourthWidget().getId());

    assertTrue(firstWidget.isPresent());
    assertTrue(secondWidget.isPresent());
    assertTrue(thirdWidget.isPresent());
    assertTrue(fourthWidget.isPresent());

    //z should stay at prev value
    assertEquals(1, firstWidget.get().getZ());
    assertEquals(2, secondWidget.get().getZ());
    assertEquals(3, thirdWidget.get().getZ());
    assertEquals(5, fourthWidget.get().getZ());

  }

  @Test
  void saveWithShift() throws WidgetNotFoundException {
    Widget newWidget = new Widget(null, 89, 45, 2, 50, 10, ZonedDateTime.now());
    newWidget = getWidgetRepository().save(newWidget);

    assertNotNull(newWidget);
    assertNotNull(newWidget.getId());
    assertEquals(5l, newWidget.getId());
    assertEquals(89, newWidget.getX());
    assertEquals(45, newWidget.getY());
    assertEquals(2, newWidget.getZ());
    assertEquals(50, newWidget.getWidth());
    assertEquals(10, newWidget.getHeight());

    Optional<Widget> firstWidget = getWidgetRepository().findById(getFirstWidget().getId());
    Optional<Widget>  secondWidget = getWidgetRepository().findById(getSecondWidget().getId());
    Optional<Widget>  thirdWidget = getWidgetRepository().findById(getThirdWidget().getId());
    Optional<Widget>  fourthWidget = getWidgetRepository().findById(getFourthWidget().getId());

    assertTrue(firstWidget.isPresent());
    assertTrue(secondWidget.isPresent());
    assertTrue(thirdWidget.isPresent());
    assertTrue(fourthWidget.isPresent());

    //z should stay at prev value
    assertEquals(1, firstWidget.get().getZ());

    //z should be shifted
    assertEquals(3, secondWidget.get().getZ());
    assertEquals(4, thirdWidget.get().getZ());

    //z should stay at prev value
    assertEquals(5, fourthWidget.get().getZ());
  }


  @Test
  void updateWithShift() throws WidgetNotFoundException {
    Widget existsWidget = new Widget(1l, 89, 45, 3, 50, 10, ZonedDateTime.now());
    existsWidget = getWidgetRepository().save(existsWidget);

    assertNotNull(existsWidget);
    assertNotNull(existsWidget.getId());
    assertEquals(1l, existsWidget.getId());
    assertEquals(89, existsWidget.getX());
    assertEquals(45, existsWidget.getY());
    assertEquals(3, existsWidget.getZ());
    assertEquals(50, existsWidget.getWidth());
    assertEquals(10, existsWidget.getHeight());

    Optional<Widget> firstWidget = getWidgetRepository().findById(getFirstWidget().getId());
    Optional<Widget>  secondWidget = getWidgetRepository().findById(getSecondWidget().getId());
    Optional<Widget>  thirdWidget = getWidgetRepository().findById(getThirdWidget().getId());
    Optional<Widget>  fourthWidget = getWidgetRepository().findById(getFourthWidget().getId());

    assertTrue(firstWidget.isPresent());
    assertTrue(secondWidget.isPresent());
    assertTrue(thirdWidget.isPresent());
    assertTrue(fourthWidget.isPresent());

    //z should stay at prev value
    assertEquals(3, firstWidget.get().getZ());

    //z should be shifted
    assertEquals(2, secondWidget.get().getZ());
    assertEquals(4, thirdWidget.get().getZ());

    //z should stay at prev value
    assertEquals(5, fourthWidget.get().getZ());
  }

  @Test
  void updateWithException() {
    Widget wrongWidget = new Widget(70l, 89, 45, 2, 50, 10, ZonedDateTime.now());
    assertThrows(WidgetNotFoundException.class, () -> {getWidgetRepository().save(wrongWidget);});
  }

  @Test
  void delete() {
    Long id = 1l;
    Optional<Widget> optionalWidget = getWidgetRepository().findById(id);
    assertTrue(optionalWidget.isPresent());

    getWidgetRepository().delete(id);

    optionalWidget = getWidgetRepository().findById(id);
    assertFalse(optionalWidget.isPresent());
  }


  @Test
  void testFindAllWithPropagation() {
    Integer pageNumber = 0;
    Integer pageSize = 2;

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Widget> widgets = getWidgetRepository().findAll(pageable);

    assertNotNull(widgets);
    assertEquals(2, widgets.size());

    Widget firstWidget = widgets.get(0);
    assertEquals(1l, firstWidget.getId());
  }

  @Test
  void testFindAllWithDashboard() {
    Dashboard dashboard = new Dashboard(10, 100, 90, 85);

    List<Widget> widgets = getWidgetRepository().findAll(dashboard);
    assertNotNull(widgets);
    assertEquals(2, widgets.size());

    Widget firstWidget = widgets.get(0);
    assertEquals(2l, firstWidget.getId());
    Widget secondWidget = widgets.get(1);
    assertEquals(3l, secondWidget.getId());

  }

  @Test
  void testFindAllWithPropagationFromMiddle() {
    Integer pageNumber = 1;
    Integer pageSize = 2;

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Widget> widgets = getWidgetRepository().findAll(pageable);

    assertNotNull(widgets);
    assertEquals(2, widgets.size());

    Widget firstWidget = widgets.get(0);
    assertEquals(3l, firstWidget.getId());
  }

  @Test
  void testFindAllWithPropagationOutOfRange() {
    Integer pageNumber = 10;
    Integer pageSize = 2;

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Widget> widgets = getWidgetRepository().findAll(pageable);

    assertNotNull(widgets);
    assertEquals(0, widgets.size());
  }

  @Test
  void testFindAllWithPropagationWithWholeRange() {
    Integer pageNumber = 0;
    Integer pageSize = 10;

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Widget> widgets = getWidgetRepository().findAll(pageable);

    assertNotNull(widgets);
    assertEquals(4, widgets.size());
  }


}