#ifndef PLOT2D_H
#define PLOT2D_H

#include <QWidget>

class QPointF;
class QPaintEvent;

class CoordinateTransformer;
class Drawer;
class StyleProvider;
class Axes;

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
   * @param xRange         The initial range of the x axis of the new plot
   *                       (defaults to -1 to 1).
   * @param yRange         The initial range of the y axis of the new plot
   *                       (defaults to -1 to 1).
   * @param styleProvider  The style provider that will be used to determine the
   *                       look of the new plot.
   */
  Plot2d(QPointF* xRange = new QPointF(-1.0, 1.0),
         QPointF* yRange = new QPointF(-1.0, 1.0),
         StyleProvider* styleProvider = new StyleProvider());

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

  /**
   * Add coordinate axes to this plot. In the future there will be default axes.
   *
   * @param axes  The coordinate axes to set for this plot.
   */
  void setAxes(Axes* axes);

protected:
  void paintEvent(QPaintEvent* event);

private:
  Axes* axes;
  std::list<Drawer*> mDrawers;
  QPointF* mxRange;
  QPointF* myRange;
  CoordinateTransformer* mTransformer;
  StyleProvider* mStyleProvider;

};

#endif // PLOT2D_H
