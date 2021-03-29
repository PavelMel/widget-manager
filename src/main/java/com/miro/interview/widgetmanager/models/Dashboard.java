package com.miro.interview.widgetmanager.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dashboard {

  @NonNull
  private Integer x;

  @NonNull
  private Integer y;

  @NonNull
  private Integer width;

  @NonNull
  private Integer height;
}
