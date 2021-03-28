package com.miro.interview.widgetmanager.repositories.db;

import com.miro.interview.widgetmanager.models.Widget;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("db")
public interface CRUDWidgetRepository  extends CrudRepository<Widget, Long> {

  Optional<Widget> findByZ(Integer z);

  List<Widget> findAllByZ(Integer z);

  List<Widget> findAllByZGreaterThanEqual(Integer z);
}
