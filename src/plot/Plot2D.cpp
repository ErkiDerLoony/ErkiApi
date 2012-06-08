#include "Plot2D.hpp"

#include "drawers/Drawer.hpp"
#include "CoordinateTransformer.hpp"

Plot2D::Plot2D(Range<double> xRange, Range<double> yRange) :
transformer(CoordinateTransformer(xRange, yRange,
                                  Range<int>(0, width()),
                                  Range<int>(0, height())))
{
  // Nothing else to do here.
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
