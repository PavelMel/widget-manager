package com.miro.interview.widgetmanager.utils;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class StreamUtilsTest {

  @Test
  void toList() {
    List<String> strings = Arrays.asList("one", "two", "three");
    List<String> reconstructedStrings = StreamUtils.toList(strings);

    assertEquals(strings, reconstructedStrings);
  }
}