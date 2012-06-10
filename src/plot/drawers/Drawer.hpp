#ifndef DRAWER_HPP
#define DRAWER_HPP

#include "CoordinateTransformer.hpp"

#include <Qt/qpainter.h>

/**
 * This class is an interface for all drawers that want to display something
 * in a {@link Plot2D}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class Drawer {

public:

  virtual ~Drawer() {}

  /**
   * This method is called by Plot2D to trigger a repaint of the contents of
   * this drawer onto the plot.
   *
   * @param painter      The painter that shall be used to draw upon the plot.
   *                     Make sure coordinates are transformed appropriately
   *                     using the given coordinate transformer instance before
   *                     actually drawing anything with this painter.
   * @param transformer  The transformer that shall be used to transform
   *                     mathematical coordinates to java screen coordinates and
   *                     vice versa.
   */
  virtual void draw(QPainter& painter, CoordinateTransformer& transformer) = 0;

};

#endif /* DRAWER_HPP */
