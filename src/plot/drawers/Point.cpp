#include "Point.hpp"

void Point::draw(QPainter painter, CoordinateTransformer transformer) {
  painter.setPen(Qt::blue);
  painter.drawLine(0, 0, 100, 100);
}
