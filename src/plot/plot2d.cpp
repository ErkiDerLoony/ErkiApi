#include "plot2d.hpp"

#include "drawers/drawer.hpp"

plot2d::plot2d(std::pair<double, double> x_range,
               std::pair<double, double> y_range) {
}

plot2d::~plot2d() {
  std::list<drawer*>::iterator it;

  for (it = drawers.begin(); it != drawers.end(); it++) {
    delete *it;
  }
}

void plot2d::add(drawer* drawer) {
  drawers.push_back(drawer);
}

#include "plot2d.moc"
