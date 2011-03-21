#include <QPainter>

#include "Drawer.hpp"
#include "CoordinateTransformer.hpp"
#include "Point.hpp"

Point::Point(double x, double y) : mX(x), mY(y) {}

void Point::draw(QPainter& painter, CoordinateTransformer& transformer) {
  painter.setPen(Qt::blue);
  painter.drawLine(0, 0, 100, 100);
}
