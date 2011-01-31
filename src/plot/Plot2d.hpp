#ifndef PLOT2D_H
#define PLOT2D_H

#include <QPointF>
#include <QWidget>

#include "Drawer.hpp"

/**
 * This class represents a 2D plot that can easily be extended by adding
 * instances of {@link Drawer}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class Plot2d : public QWidget {

Q_OBJECT

public:

  /** Create a new plot with default ranges (-1 to 1). */
  Plot2d();

  /**
   * Create a new plot with given initial ranges.
   *
   * @param xRange  the initial range of the x axis of the new plot
   * @param yRange  the initial range of the y axis of the new plot
   */
  Plot2d(QPointF* xRange, QPointF* yRange);

  /**
   * Destroy this plot. All remaining drawers will be deleted and their
   * ressources freed.
   */
  ~Plot2d();

  /**
   * Add a new drawer to this plot.
   *
   * @param drawer  the new drawer to add
   */
  void add(Drawer* drawer);

private:
  std::list<Drawer*> mDrawers;
  QPointF* mxRange;
  QPointF* myRange;

};

#endif // PLOT2D_H
