#ifndef MOVE_HPP
#define MOVE_HPP

#include "Action.hpp"

#include <Qt/qevent.h>

class Move : public Action {

  Q_OBJECT

public:

  Move(const Plot2D& plot);

  virtual ~Move() {};

public slots:

  void pressed(QMouseEvent* event);

};

#endif /* MOVE_HPP */
