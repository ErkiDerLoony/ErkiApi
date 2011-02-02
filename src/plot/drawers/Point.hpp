#ifndef POINT_H
#define POINT_H

#include "Drawer.hpp"

class Point : public Drawer {

public:
  virtual ~Point() {};
  virtual void draw(QPainter painter, CoordinateTransformer transformer);

};

#endif // POINT_H
