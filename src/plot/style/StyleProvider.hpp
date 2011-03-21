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

/* Forward declarations. */
template<typename T> class Key;
class Object;

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

  /**
   * Create a new StyleProvider that contains default values for all constants
   * necessary for {@link Plot2d}.
   */
  StyleProvider();

  /** Delete this StyleProvider and clean up all contained values. */
  virtual ~StyleProvider();

  /**
   * Add a new value to this StyleProvider.
   *
   * @param pair  The key/value pair to add.
   */
  template<typename T> void add(std::pair<Key<T>, T> pair);

  /**
   * Add a new value to this StyleProvider.
   *
   * @param key    The unique key under which the new value will be stored.
   * @param value  The new value.
   */
  template<typename T> void add(Key<T> key, T value);

  /**
   * Check whether a value is stored under some key.
   *
   * @param key  The key to check.
   */
  template<typename T> bool contains(Key<T> key);

private:
  std::map<Key<Object>, Object> values;

};

#endif /* STYLE_PROVIDER_H */
