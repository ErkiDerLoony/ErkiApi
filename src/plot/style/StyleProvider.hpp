#ifndef STYLE_PROVIDER_H
#define STYLE_PROVIDER_H

/* Forward declaration. */
class Object;

/* Inevitable includes. */
#include <map>

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

/**
 * This class is used to retain the type of style constant stored in an instance
 * of {@link StyleProvider}. The actual type is not used when comparing two keys
 * so make sure the used identifier (see {@link Keys}) is unique.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
template<class T> class Key {

  Keys mId;

public:
  Key(Keys id) : mId(id) {}
  virtual ~Key() {}

  template<class O> operator Key<O>() { return Key<O>(mId); }

  bool operator<(const Key<T> other) { return mId < other.mId; }

};

/**
 * This class is used to store style constants that define the look of a
 * {@link Plot2d}.
 *
 * @autor Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class StyleProvider {

  std::map<Key<Object>, Object> values;

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
   * @param key    The unique key under which the new value will be stored.
   * @param value  The new value.
   */
  template<class T> void add(Key<T> key, T value);

  /**
   * Check whether a value is stored under some key.
   *
   * @param key  The key to check.
   */
  template<class T> bool contains(Key<T> key);

};

#endif /* STYLE_PROVIDER_H */
