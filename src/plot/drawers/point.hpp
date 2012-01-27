#ifndef POINT_HPP
#define POINT_HPP

#include "drawer.hpp"

class point : public drawer {

public:
  point(double x, double y);
  virtual ~point() {};
  virtual void draw();

private:
  double x, y;

};

#endif // POINT_HPP
