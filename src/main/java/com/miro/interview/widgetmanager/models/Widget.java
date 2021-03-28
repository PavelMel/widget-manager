package com.miro.interview.widgetmanager.models;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "widget")
public class Widget {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private Integer x;

  @NonNull
  private Integer y;

  @NonNull
  private Integer z;

  @NonNull
  private Integer width;

  @NonNull
  private Integer height;

  private ZonedDateTime lastModificationDate;

  public void incrementZ(){
    this.z++;
  }

  public void update(@NonNull Widget widget){
    if (widget.getX() != null){
      this.x = widget.getX();
    }
    if (widget.getY() != null){
      this.y = widget.getY();
    }
    if (widget.getZ() != null){
      this.z = widget.getZ();
    }
    if (widget.getHeight() != null){
      this.height = widget.getHeight();
    }
    if (widget.getWidth() != null){
      this.width = widget.getWidth();
    }
    this.lastModificationDate = ZonedDateTime.now();
  }

}
