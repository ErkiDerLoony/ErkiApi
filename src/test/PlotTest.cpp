#include <QApplication>

#include "Plot2d.hpp"
#include "Point.hpp"

int main(int argc, char** argv) {
  QApplication app(argc, argv);
  Plot2d* plot = new Plot2d();
  plot->add(new Point());
  plot->show();
  return app.exec();
}
