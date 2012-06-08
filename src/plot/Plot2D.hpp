#ifndef PLOT2D_HPP
#define PLOT2D_HPP

/* Standard includes. */
#include <utility>
#include <list>

/* Qt includes. */
#include <Qt/qwidget.h>

/* Forward declarations. */
class Drawer;

/* Necessary other includes. */
#include "CoordinateTransformer.hpp"

/**
 * This class represents a 2D plot that can easily be extended by adding
 * instances of {@link Drawer}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class Plot2D : public QWidget {

Q_OBJECT

public:

  /**
   * Create a new plot with given initial ranges.
   *
   * @param xRange         The initial range of the x axis of the new plot
   *                       (defaults to -1 to 1).
   * @param yRange         The initial range of the y axis of the new plot
   *                       (defaults to -1 to 1).
   */
  Plot2D(Range<double> xRange = Range<double>(-1.0, 1.0),
         Range<double> yRange = Range<double>(-1.0, 1.0));

  /**
   * Destroy this plot. All remaining drawers will be deleted and their
   * ressources freed.
   */
  ~Plot2D();

  /**
   * Add a new drawer to this plot.
   *
   * @param drawer  the new drawer to add
   */
  void add(Drawer* drawer);

protected:

  void paintEvent(QPaintEvent* event);

private:
  CoordinateTransformer transformer;
  std::list<Drawer*> drawers;

};

#endif /* PLOT2D_HPP */
