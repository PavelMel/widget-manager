package com.miro.interview.widgetmanager.repositories.db;

import com.miro.interview.widgetmanager.repositories.IWidgetRepository;
import com.miro.interview.widgetmanager.repositories.memory.InMemoryWidgetRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("db")
class DBWidgetRepositoryTest extends InMemoryWidgetRepositoryTest {

  @Autowired
  private IWidgetRepository widgetRepository;

  @Autowired
  private CRUDWidgetRepository widgetCRUDRepository;

  @Override
  public IWidgetRepository getWidgetRepository(){
    return this.widgetRepository;
  }

  @Override
  @BeforeEach
  public void init(){
    widgetCRUDRepository.deleteAll();

    widgetCRUDRepository.save(firstWidget);
    widgetCRUDRepository.save(secondWidget);
    widgetCRUDRepository.save(thirdWidget);
    widgetCRUDRepository.save(fourthWidget);
  }


}