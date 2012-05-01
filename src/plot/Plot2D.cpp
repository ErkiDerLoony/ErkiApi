#include "Plot2D.hpp"

#include "drawers/Drawer.hpp"
#include "CoordinateTransformer.hpp"

Plot2D::Plot2D(std::pair<double, double> x_range,
               std::pair<double, double> y_range)
  : transformer(CoordinateTransformer(*this)) {
}

Plot2D::~Plot2D() {
  std::list<Drawer*>::iterator it;

  for (it = drawers.begin(); it != drawers.end(); it++) {
    delete *it;
  }
}

void Plot2D::add(Drawer* drawer) {
  drawers.push_back(drawer);
}

void Plot2D::paintEvent(QPaintEvent* event) {
  QPainter painter(this);
  painter.setRenderHint(QPainter::Antialiasing, true);
  painter.setRenderHint(QPainter::TextAntialiasing, true);

  transformer.setXPixelRange(Range<int>(0, width()));
  transformer.setYPixelRange(Range<int>(0, height()));

  std::list<Drawer*>::iterator it;

  for (it = drawers.begin(); it != drawers.end(); it++) {
    (*it)->draw(painter, transformer);
  }
}

#include "Plot2D.moc"
