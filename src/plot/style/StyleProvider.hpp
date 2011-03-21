#ifndef STYLE_PROVIDER_H
#define STYLE_PROVIDER_H

/**
 * This enum is used to uniquely identify style constants stored in a
 * StyleProvider.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
enum Keys {

  /** The background colour for the plot as an instance of QColor. */
  BACKGROUND

};

/* Forward declaration. */
template <typename T> class Key;

/* Inevitable includes. */
#include <map>

/**
 * This class is used to store style constants that define the look of a
 * {@link Plot2d}.
 *
 * @autor Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class StyleProvider {

public:
  StyleProvider();
  virtual ~StyleProvider();

private:
  std::map<Key<double>, double> values;

};

#endif /* STYLE_PROVIDER_H */
