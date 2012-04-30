#ifndef PLOT2D_HPP
#define PLOT2D_HPP

#include <utility>
#include <list>
#include <Qt/qwidget.h>

class drawer;

/**
 * This class represents a 2D plot that can easily be extended by adding
 * instances of {@link drawer}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class plot2d : public QWidget {

Q_OBJECT

public:

  /**
   * Create a new plot with given initial ranges.
   *
   * @param x_range         The initial range of the x axis of the new plot
   *                        (defaults to -1 to 1).
   * @param y_range         The initial range of the y axis of the new plot
   *                        (defaults to -1 to 1).
   */
  plot2d(std::pair<double, double> x_range = std::pair<double, double>(-1.0, 1.0),
         std::pair<double, double> y_range = std::pair<double, double>(-1.0, 1.0));

  /**
   * Destroy this plot. All remaining drawers will be deleted and their
   * ressources freed.
   */
  ~plot2d();

  /**
   * Add a new drawer to this plot.
   *
   * @param drawer  the new drawer to add
   */
  void add(drawer* drawer);

private:
  std::list<drawer*> drawers;

};

#endif /* PLOT2D_HPP */
