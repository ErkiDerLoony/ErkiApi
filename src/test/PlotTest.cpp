#include <QApplication>

#include "Plot2d.hpp"
#include "ArrowAxes.hpp"

int main(int argc, char** argv) {
  QApplication app(argc, argv);
  Plot2d* plot = new Plot2d();
  plot->add(new ArrowAxes());
  plot->show();
  return app.exec();
}
