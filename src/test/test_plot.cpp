#include "plot/plot2d.hpp"

#include <Qt/qapplication.h>

int main(int argc, char** argv) {
  QApplication app(argc, argv);

  plot2d* plot = new plot2d();
  plot->show();

  return app.exec();
}
