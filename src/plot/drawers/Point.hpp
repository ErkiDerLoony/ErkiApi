#ifndef POINT_H
#define POINT_H

class QPainter;

class Drawer;
class CoordinateTransformer;

class Point : public Drawer {

public:
  Point(double x, double y);
  virtual ~Point() {};
  virtual void draw(QPainter* painter, CoordinateTransformer* transformer);

private:
  double mX, mY;

};

#endif // POINT_H
