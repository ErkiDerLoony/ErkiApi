#include <iostream>

#include "Plot2d.hpp"
#include "Plot2d.moc"

Plot2d::Plot2d() : mxRange(new QPointF(-1.0, 1.0)),
                    myRange(new QPointF(-1.0, 1.0)) {
}

Plot2d::Plot2d(QPointF* xRange, QPointF* yRange)
  : mxRange(xRange), myRange(yRange) {
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
