package com.miro.interview.widgetmanager.utils;

import com.miro.interview.widgetmanager.models.Dashboard;
import com.miro.interview.widgetmanager.models.Widget;

/**
 * Let 𝑃(𝑥,𝑦), and rectangle 𝐴(𝑥1,𝑦1),𝐵(𝑥2,𝑦2),𝐶(𝑥3,𝑦3),𝐷(𝑥4,𝑦4)
 * Calculate the sum of areas of △𝐴𝑃𝐷,△𝐷𝑃𝐶,△𝐶𝑃𝐵,△𝑃𝐵𝐴.
 *
 * If this sum is greater than the area of the rectangle, then 𝑃(𝑥,𝑦) is outside the rectangle.
 * Else if this sum is equal to the area of the rectangle (observe that this sum cannot be less than the latter),
 *
 * if area of any of the triangles is 0, then 𝑃(𝑥,𝑦) is on the rectangle (in fact on that line corresponding to the triangle of area=0). Observe that the equality of the sum is necessary; it is not sufficient that area=0),
 * else 𝑃(𝑥,𝑦) is is inside the rectangle.
 *
 * Acceptably this approach needs substantial amount of computation. This approach can be employed to any irregular polygon, too.
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
