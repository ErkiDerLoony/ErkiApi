#ifndef ARROW_AXES_H
#define ARROW_AXES_H

#include "Drawer.hpp"

/**
 * This {@link Drawer} displays simple horizontal and vertical axes that end in
 * a little arrow.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class ArrowAxes : public Drawer {

public:

  ~ArrowAxes() {};

  void draw(QPainter* painter, CoordinateTransformer* transformer);

};

#endif // ARROW_AXES_H
