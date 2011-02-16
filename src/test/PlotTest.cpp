#include <QApplication>

#include "Drawer.hpp"
#include "Plot2d.hpp"
#include "ArrowAxes.hpp"
#include "Point.hpp"

int main(int argc, char** argv) {
  QApplication app(argc, argv);
  Plot2d* plot = new Plot2d();
  plot->add(new ArrowAxes());
  plot->add(new Point(0.0, 0.0));
  plot->show();
  return app.exec();
}
