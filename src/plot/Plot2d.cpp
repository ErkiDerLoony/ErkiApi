#include <iostream>

#include "Plot2d.hpp"
#include "Plot2d.moc"

Plot2d::Plot2d(QPointF* xRange, QPointF* yRange)
  : mxRange(xRange), myRange(yRange),
    mTransformer(new CoordinateTransformer()) {
}

Plot2d::~Plot2d() {
  std::list<Drawer*>::iterator it;

  for (it = mDrawers.begin(); it != mDrawers.end(); it++) {
    delete *it;
  }
}

void Plot2d::add(Drawer* drawer) {
  mDrawers.push_back(drawer);
}

void Plot2d::paintEvent(QPaintEvent* event) {
  std::list<Drawer*>::iterator it;
  QPainter* painter = new QPainter(this);

  for (it = mDrawers.begin(); it != mDrawers.end(); it++) {
    (*it)->draw(painter, NULL);
  }
}
