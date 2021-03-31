package com.miro.interview.widgetmanager.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dashboard {

  @NotNull
  private Integer x;

  @NotNull
  private Integer y;

  @NotNull
  @Positive
  private Integer width;

  @NotNull
  @Positive
  private Integer height;
}
