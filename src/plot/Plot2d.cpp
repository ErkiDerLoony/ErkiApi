#include <QPointF>
#include <QPainter>

#include "Drawer.hpp"
#include "CoordinateTransformer.hpp"
#include "StyleProvider.hpp"
#include "Plot2d.hpp"
#include "Plot2d.moc"

Plot2d::Plot2d(QPointF* xRange, QPointF* yRange, StyleProvider* styleProvider)
  : mxRange(xRange), myRange(yRange),
    mTransformer(new CoordinateTransformer(this)),
    mStyleProvider(styleProvider) {
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
  QPainter painter(this);

  for (it = mDrawers.begin(); it != mDrawers.end(); it++) {
    (*it)->draw(painter, *mTransformer);
  }
}
