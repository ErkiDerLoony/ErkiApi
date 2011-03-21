#include <QColor>

#include "Object.hpp"

/**
 * This is a wrapper class for QColor which makes it possible to store it as a
 * value in an instance of {@link StyleProvider}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class Colour : public Object {

  QColor mColour;

public:

  Colour(QColor colour) : mColour(colour) {}

  virtual ~Colour() {}

  QColor get() {
    return mColour;
  }

};
