#ifndef LINE_HPP
#define LINE_HPP

#include "Drawer.hpp"

#include <Qt/qpoint.h>

class Line : public Drawer {

public:

  Line(QPointF from, QPointF to);

  virtual ~Line();

  void draw(QPainter& painter, CoordinateTransformer& transformer);

private:

  QPointF mFrom;
  QPointF mTo;

};

#endif
