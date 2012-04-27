#ifndef COORDINATE_TRANSFORMER_HPP
#define COORDINATE_TRANSFORMER_HPP

/* Forward declarations. */
class plot2d;
class QPoint;
class QPointF;

/**
 * This class is used by {@link Plot2d} to translate coordinates from math
 * coordinates to screen coordinates that can be drawn using a {@link QPainter}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class coordinate_transformer {

public:

  /**
   * Create a new coordinate_transformer.
   *
   * @param plot  The plot instance this transformer belongs to.
   */
  coordinate_transformer(plot2d* plot);

  /**
   * Translate math coordinates to screen coordinates.
   *
   * @param point  a point in math coordinates; not deleted by this method
   * @return       a point on the screen that corresponds to the given
   *               mathematical point; has to be deleted after use to free
   *               memory
   */
  QPoint* screen(QPointF* point);

  /**
   * Translate screen coordinates to math coordinates.
   *
   * @param point  a point in screen coordinates; not deleted by this method
   * @return       the math coordinates of the given point; has to be deleted
   *               after use to free memory
   */
  QPointF* math(QPoint* point);

private:
  plot2d* mPlot;

};

#endif /* COORDINATE_TRANSFORMER_HPP */
