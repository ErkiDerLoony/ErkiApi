#include <QApplication>
#include <QGridLayout>

#include "LcarsFrame.hpp"
#include "Drawer.hpp"
#include "Plot2d.hpp"
#include "ArrowAxes.hpp"
#include "Point.hpp"

int main(int argc, char** argv) {
  QApplication app(argc, argv);

  QString title("Testframe");
  LcarsFrame* frame = new LcarsFrame(title);

  Plot2d* plot = new Plot2d();
  plot->add(new ArrowAxes());
  plot->add(new Point(0.0, 0.0));

  QGridLayout* layout = new QGridLayout();
  //layout->addWidget(plot, 0, 0);

  frame->setLayout(layout);
  frame->show();

  return app.exec();
}
