#ifndef POINT_HPP
#define POINT_HPP

#include "Drawer.hpp"
#include "plot/CoordinateTransformer.hpp"

#include <Qt/qpainter.h>

/**
 * This drawer marks one point in the plot with a little cross.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class Point : public Drawer {

public:

  /**
   * Create a new Point.
   *
   * @param x  The first coordinate of the point that shall be marked.
   * @param y  The second coordinate of the point that shall be marked.
   */
  Point(double x, double y);

  /** Destroys this drawer and releases acquired ressources. */
  virtual ~Point() {};

  virtual void draw(QPainter& painter, CoordinateTransformer& transformer);

private:
  double mX;
  double mY;

};

#endif /* POINT_HPP */
