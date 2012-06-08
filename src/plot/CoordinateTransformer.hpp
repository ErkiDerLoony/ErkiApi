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
   */
  CoordinateTransformer(Range<double>, Range<double>, Range<int>, Range<int>);

  virtual ~CoordinateTransformer() {};

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

  const Range<double>& xRange();
  void setXRange(const Range<double>& xRange);

  const Range<double>& yRange();
  void setYRange(const Range<double>& yRange);

  const Range<int>& xPixelRange();
  void setXPixelRange(const Range<int>& xPixelRange);

  const Range<int>& yPixelRange();
  void setYPixelRange(const Range<int>& yPixelRange);

private:
  Range<double> mXRange;
  Range<double> mYRange;
  Range<int> mXPixelRange;
  Range<int> mYPixelRange;

};

#endif /* COORDINATE_TRANSFORMER_HPP */
