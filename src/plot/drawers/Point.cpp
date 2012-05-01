#include "Point.hpp"

Point::Point(double x, double y) : mX(x), mY(y) {}

void Point::draw(QPainter& painter, CoordinateTransformer& transformer) {
  QPoint p;
  transformer.screen(QPointF(mX, mY), p);
  painter.drawRect(p.x() - 5, p.y() - 5, 10, 10);
}
