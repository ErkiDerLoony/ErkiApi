#ifndef ARROW_AXES_H
#define ARROW_AXES_H

#include "Axes.hpp"

class QPainter;

class Drawer;
class CoordinateTransformer;

/**
 * This {@link Drawer} displays simple horizontal and vertical axes that end in
 * a little arrow.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class ArrowAxes : public Axes {

public:

  virtual ~ArrowAxes();

  virtual double getMinX();
  virtual void setMinX(double);

  virtual double getMaxX();
  virtual void setMaxX(double);

  virtual double getMinY();
  virtual void setMinY(double);

  virtual double getMaxY();
  virtual void setMaxY(double);

  virtual void draw(QPainter&, CoordinateTransformer&);

};

#endif // ARROW_AXES_H
