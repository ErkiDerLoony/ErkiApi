#include "plot/Plot2D.hpp"
#include "plot/drawers/Point.hpp"
#include "plot/drawers/Line.hpp"

#include <Qt/qapplication.h>

int main(int argc, char** argv) {
  QApplication app(argc, argv);

  Plot2D plot;
  plot.add(new Point(0.5, 0.5));
  plot.add(new Line(QPointF(0.0, 0.0), QPointF(0.5, 0.25)));
  plot.show();

  return app.exec();
}
