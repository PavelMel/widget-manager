package com.miro.interview.widgetmanager.repositories.db;

import com.miro.interview.widgetmanager.models.Widget;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("db")
public interface CRUDWidgetRepository  extends PagingAndSortingRepository<Widget, Long> {

  Optional<Widget> findByZ(Integer z);

  List<Widget> findAllByZGreaterThanEqual(Integer z);

  List<Widget> findAllByWidthIsLessThanEqualAndHeightIsLessThanEqual(Integer width, Integer height);

  @Query("SELECT max(w.z) FROM Widget w")
  Integer getMaxZIndex();
}
