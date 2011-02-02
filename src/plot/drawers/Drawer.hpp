#ifndef DRAWER_H
#define DRAWER_H

#include <QPainter>

#include "CoordinateTransformer.hpp"

/**
 * This class is an interface for all drawers that want to display something
 * in a Plot2d.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class Drawer {

public:

  virtual ~Drawer() {};

  /**
   * This method is called by Plot2d to trigger a repaint of the contents of
   * this drawer onto the plot.
   *
   * @param painter      This painter can be used to draw stuff on the plot.
   *                     Before actual drawing the coordinates of objects must
   *                     be transformed from mathematical coordinates to screen
   *                     coordinates using the given coordinate transformer.
   * @param transformer  This transformer can be used to transform math
   *                     coordinates into screen coordinates that can be used
   *                     to actually draw with the given painter.
   */
  virtual void draw(QPainter painter, CoordinateTransformer transformer) = 0;

};

#endif // DRAWER_H
