package com.miro.interview.widgetmanager.utils;

import com.miro.interview.widgetmanager.models.Dashboard;
import com.miro.interview.widgetmanager.models.Widget;

/**
 * The main idea is that having point inside rectangle we can divide rectangle in four triangle and sum their area == rectangle area
 *
 * If we get point π(π₯,π¦), and rectangle π΄(π₯1,π¦1),π΅(π₯2,π¦2),πΆ(π₯3,π¦3),π·(π₯4,π¦4)
 * we can calculate the sum of areas of β³π΄ππ·,β³π·ππΆ,β³πΆππ΅,β³ππ΅π΄.
 *
 * If this sum is greater than the area of the rectangle, then π(π₯,π¦) is outside the rectangle.
 * Else if this sum is equal to the area of the rectangle
 *
 */
public class DashboardUtil {

  public static float getArea(int x1, int y1, int x2,
                              int y2, int x3, int y3) {
    return (float)Math.abs((x1 * (y2 - y3)
                            + x2 * (y3 - y1)
                            + x3 * (y1 - y2)) / 2.0);
  }

  public static boolean isWidgetInside(Dashboard dashboard, Widget widget){
    int x1 = widget.getX();
    int y1 = widget.getY();

    int x2 = widget.getX() + widget.getWidth();
    int y2 = widget.getY() - widget.getHeight();

    return isPointInside(dashboard, x1, y1) && isPointInside(dashboard, x2, y2);
  }

  public static boolean isPointInside(Dashboard dashboard, int x, int y){
    int x1 = dashboard.getX();
    int y1 = dashboard.getY();

    int x2 = dashboard.getX() + dashboard.getWidth();
    int y2 = dashboard.getY();

    int x3 = dashboard.getX() + dashboard.getWidth();
    int y3 = dashboard.getY() - dashboard.getHeight();

    int x4 = dashboard.getX();
    int y4 = dashboard.getY() - dashboard.getHeight();

    /* Calculate area of rectangle ABCD */
    float A = getArea(x1, y1, x2, y2, x3, y3) + getArea(x1, y1, x4, y4, x3, y3);

    /* Calculate area of triangle PAB */
    float A1 = getArea(x, y, x1, y1, x2, y2);

    /* Calculate area of triangle PBC */
    float A2 = getArea(x, y, x2, y2, x3, y3);

    /* Calculate area of triangle PCD */
    float A3 = getArea(x, y, x3, y3, x4, y4);

    /* Calculate area of triangle PAD */
    float A4 = getArea(x, y, x4, y4, x1, y1);


    /* Check if sum of A1, A2, A3 and A4 is same as A */
    return (A == A1 + A2 + A3 + A4);
  }

}
