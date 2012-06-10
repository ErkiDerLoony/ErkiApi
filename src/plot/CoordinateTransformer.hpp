#ifndef COORDINATE_TRANSFORMER_HPP
#define COORDINATE_TRANSFORMER_HPP

/* Forward declarations. */
class Plot2D;
class QPoint;
class QPointF;

#include "Range.hpp"

/**
 * This class is used by {@link Plot2D} to translate coordinates between a
 * mathematical cartesian coordinate system and Qt’s pixel coordinates used for
 * drawing operations on the screen.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class CoordinateTransformer {

public:

  /**
   * Create a new CoordinateTransformer.
   *
   * @param xRange       The horizontal range of the mathematical cartesian
   *                     coordinate system.
   * @param yRange       The vertical range of the mathematical cartesian
   *                     coordinate system.
   * @param xPixelRange  The horizontal range of the screen coordinate system.
   * @param yPixelRange  The vertical range of the screen coordinate system.
   */
  CoordinateTransformer(Range<double> xRange, Range<double> yRange,
                        Range<int> xPixelRange, Range<int> yPixelRange);

  virtual ~CoordinateTransformer() {};

  /**
   * Translate math coordinates to screen coordinates.
   *
   * @param src  A point in math coordinates.
   * @param dst  A point on the screen that corresponds to the given
   *             mathematical point.
   */
  void screen(const QPointF& src, QPoint& dst) const;

  /**
   * Translate screen coordinates to math coordinates.
   *
   * @param src  A point in screen coordinates.
   * @param dst  The math coordinates of the given point.
   */
  void math(const QPoint& src, QPointF& dst) const;

  /**
   * Access the range of the horizontal axis of the math coordinate system.
   *
   * @return  The range of the horizontal axis in math coordinates.
   */
  const Range<double>& xRange() const;

  /**
   * Change the range of the horizontal axis of the math coordinate system.
   *
   * @param range  The new range of the horizontal axis of the math coordinate
   *               system.
   */
  void setXRange(const Range<double>& range);

  /**
   * Access the range of the vertical axis of the math coordinate system.
   *
   * @return  The range of the vertical axis in math coordinates.
   */
  const Range<double>& yRange() const;

  /**
   * Change the range of the vertical axis of the math coordinate system.
   *
   * @param range  The new range of the vertical axis of the math coordinate
   *               system.
   */
  void setYRange(const Range<double>& range);

  /**
   * Access the horizontal range of the screen coordinate system.
   *
   * @return  The range of the horizontal axis of the screen coordinate system.
   */
  const Range<int>& xPixelRange() const;

  /**
   * Change the range of the horizontal axis of the screen coordinate system.
   *
   * @param range  The new range of the horizontal axis of the screen coordinate
   *               system.
   */
  void setXPixelRange(const Range<int>& range);

  /**
   * Access the range of the vertical axis of the screen coordinate system.
   *
   * @return  The range of the vertical axis of the screen coordinate system.
   */
  const Range<int>& yPixelRange() const;

  /**
   * Change the range of the vertical axis of the screen coordinate system.
   *
   * @param range  The new range of the vertical axis of the screen coordinate
   *               system.
   */
  void setYPixelRange(const Range<int>& yPixelRange);

  /**
   * Access the width of the screen coordinate system.
   *
   * @return  The width of the screen coordinate system in pixels.
   */
  int width() const;

  /**
   * Access the height of the screen coordinate system.
   *
   * @return  The height of the screen coordinate system in pixels.
   */
  int height() const;

private:
  Range<double> m_xRange;
  Range<double> m_yRange;
  Range<int> m_xPixelRange;
  Range<int> m_yPixelRange;

};

#endif /* COORDINATE_TRANSFORMER_HPP */
