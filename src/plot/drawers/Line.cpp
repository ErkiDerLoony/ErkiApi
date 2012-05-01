#include "Line.hpp"

Line::Line(QPointF from, QPointF to) : mFrom(from), mTo(to) {

}

Line::~Line() {

}

void Line::draw(QPainter& painter, CoordinateTransformer& transformer) {
  QPoint from, to;
  transformer.screen(mFrom, from);
  transformer.screen(mTo, to);

  painter.drawLine(from.x(), from.y(), to.x(), to.y());
}
