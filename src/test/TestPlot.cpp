#include "plot/Plot2D.hpp"
#include "plot/drawers/Point.hpp"
#include "plot/drawers/Line.hpp"

#include <Qt/qapplication.h>

#include <iostream>

int main(int argc, char** argv) {
  QApplication app(argc, argv);

  Plot2D plot;
  plot.add(new Point(0.5, 0.5));
  plot.add(new Line(QPointF(0.0, 0.0), QPointF(0.5, 0.25)));
  plot.show();

  CoordinateTransformer transformer(Range<double>(-1.0, 1.0),
                                    Range<double>(-1.0, 1.0),
                                    Range<int>(0, 640),
                                    Range<int>(0, 480));
  QPointF src(0.0, 0.5);
  QPoint dst;
  transformer.screen(src, dst);
  std::cout << "(" << src.x() << ", " << src.y() << ") → "
            << "(" << dst.x() << ", " << dst.y() << ")" << std::endl;

  transformer.math(dst, src);
  std::cout << "(" << dst.x() << ", " << dst.y() << ") → "
            << "(" << src.x() << ", " << src.y() << ")" << std::endl;

  return app.exec();
}
