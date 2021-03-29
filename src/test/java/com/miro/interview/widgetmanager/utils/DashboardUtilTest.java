package com.miro.interview.widgetmanager.utils;

import com.miro.interview.widgetmanager.models.Dashboard;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DashboardUtilTest {

  @Test
  void testIsPointInside(){
    Dashboard dashboard = new Dashboard(0, 10, 5,5);
    int x = 2;
    int y = 7;

    assertTrue(DashboardUtil.isPointInside(dashboard, x, y));

  }

}