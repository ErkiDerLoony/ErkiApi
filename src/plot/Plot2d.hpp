#ifndef PLOT2D_H
#define PLOT2D_H

#include <QWidget>

class QPointF;
class QPaintEvent;

class CoordinateTransformer;
class Drawer;

/**
 * This class represents a 2D plot that can easily be extended by adding
 * instances of {@link Drawer}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class Plot2d : public QWidget {

Q_OBJECT

public:

  /**
   * Create a new plot with given initial ranges.
   *
   * @param xRange  the initial range of the x axis of the new plot
   *                (defaults to -1 to 1)
   * @param yRange  the initial range of the y axis of the new plot
   *                (defaults to -1 to 1)
   */
  Plot2d(QPointF* xRange = new QPointF(-1.0, 1.0),
         QPointF* yRange = new QPointF(-1.0, 1.0));

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

protected:
  void paintEvent(QPaintEvent* event);

private:
  std::list<Drawer*> mDrawers;
  QPointF* mxRange;
  QPointF* myRange;
  CoordinateTransformer* mTransformer;

};

#endif // PLOT2D_H
