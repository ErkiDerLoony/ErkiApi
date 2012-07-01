#include "Move.hpp"

#include <Qt/qobject.h>

Move::Move(const Plot2D& plot) {
  QObject::connect(&plot, SIGNAL(mousePressEvent(QMouseEvent*)),
                   this, SLOT(pressed(QMouseEvent*)));
}
