#include <QPointF>
#include <QPainter>

#include "Axes.hpp"
#include "Drawer.hpp"
#include "CoordinateTransformer.hpp"
#include "StyleProvider.hpp"
#include "Colour.hpp"

#include "Plot2d.hpp"
#include "Plot2d.moc"

Plot2d::Plot2d(QPointF* xRange, QPointF* yRange, StyleProvider* styleProvider)
  : mxRange(xRange), myRange(yRange),
    mTransformer(new CoordinateTransformer(this)),
    mStyleProvider(styleProvider) {
  QPalette p = palette();
  p.setColor(QPalette::Window,
             styleProvider->get(Key<Colour>(BACKGROUND))->get());
  setAutoFillBackground(true);
  setPalette(p);
}

Plot2d::~Plot2d() {
  std::list<Drawer*>::iterator it;

  for (it = mDrawers.begin(); it != mDrawers.end(); it++) {
    delete *it;
  }

  delete axes;
}

void Plot2d::add(Drawer* drawer) {
  mDrawers.push_back(drawer);
}

void Plot2d::setAxes(Axes* axes) {
  this->axes = axes;
}

void Plot2d::paintEvent(QPaintEvent* event) {
  std::list<Drawer*>::iterator it;
  QPainter painter(this);

  axes->draw(painter, *mTransformer);

  for (it = mDrawers.begin(); it != mDrawers.end(); it++) {
    (*it)->draw(painter, *mTransformer);
  }
}