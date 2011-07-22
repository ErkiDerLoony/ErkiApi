#ifndef AXES_H
#define AXES_H

class QPainter;
class CoordinateTransformer;

class Axes {

protected:
  double minX, maxX, minY, maxY;

public:

  virtual ~Axes() {};

  virtual double getMinX() = 0;
  virtual void setMinX(double) = 0;

  virtual double getMaxX() = 0;
  virtual void setMaxX(double) = 0;

  virtual double getMinY() = 0;
  virtual void setMinY(double) = 0;

  virtual double getMaxY() = 0;
  virtual void setMaxY(double) = 0;

  virtual void draw(QPainter&, CoordinateTransformer&) = 0;

};

#endif // AXES_H
