package com.miro.interview.widgetmanager.models;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "widget")
public class Widget {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private Integer x;

  @NotNull
  private Integer y;

  @NotNull
  private Integer z;

  @NotNull
  @Positive
  private Integer width;

  @NotNull
  @Positive
  private Integer height;

  private ZonedDateTime lastModificationDate;

  public void incrementZ(){
    this.z++;
  }

  public void update(@NotNull Widget widget){
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
