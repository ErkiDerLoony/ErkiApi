#ifndef COORDINATE_TRANSFORMER_HPP
#define COORDINATE_TRANSFORMER_HPP

/* Forward declarations. */
class Plot2D;
class QPoint;
class QPointF;

#include "Range.hpp"

/**
 * This class is used by {@link Plot2D} to translate coordinates from math
 * coordinates to screen coordinates that can be drawn using a {@link QPainter}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class CoordinateTransformer {

public:

  /**
   * Create a new CoordinateTransformer.
   *
   * @param plot  The plot instance this transformer belongs to.
   */
  CoordinateTransformer(const Plot2D& plot);

  /**
   * Translate math coordinates to screen coordinates.
   *
   * @param src  A point in math coordinates.
   * @param dst  A point on the screen that corresponds to the given
   *             mathematical point.
   */
  void screen(const QPointF& src, QPoint& dst);

  /**
   * Translate screen coordinates to math coordinates.
   *
   * @param src  A point in screen coordinates.
   * @param dst  The math coordinates of the given point.
   */
  void math(const QPoint& src, QPointF& dst);

  /**
   * Update the range of this transformer’s horizontal axis of mathematical
   * coordinates.
   *
   * @param xRange  The new range of the horizontal axis in math coordinates.
   */
  void setXRange(const Range<double>& xRange);

  /**
   * Update the range of this transformer’s vertical axis of mathematical
   * coordinates.
   *
   * @param yRange  The new range of the vertical axis in math coordinates.
   */
  void setYRange(const Range<double>& yRange);

  /**
   * Update the range of this transformer’s horizontal axis of screen
   * coordinates.
   *
   * @param xPixelRange  The new range of the horizontal axis in pixels.
   */
  void setXPixelRange(const Range<int>& xPixelRange);

  /**
   * Update the range of this transformer’s vertical axis of screen
   * coordinates.
   *
   * @param yPixelRange  The new range of the vertical axis in pixels.
   */
  void setYPixelRange(const Range<int>& yPixelRange);

private:
  const Plot2D& mPlot;
  Range<double> mXRange;
  Range<double> mYRange;
  Range<int> mXPixelRange;
  Range<int> mYPixelRange;

};

#endif /* COORDINATE_TRANSFORMER_HPP */
