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

  template<class O> operator Key<O>() const { return Key<O>(mId); }

  bool operator<(const Key<T> other) const { return mId < other.mId; }

};

/**
 * This class is used to store style constants that define the look of a
 * {@link Plot2d}.
 *
 * @autor Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class StyleProvider {

  /**
   * This map stores the internal mapping of {@link Key} instances to the
   * according values.
   */
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
  template<class T> void add(const Key<T> key, const T value);

  /**
   * Check whether a value is stored under some key.
   *
   * @param key  The key to check.
   */
  template<class T> bool contains(const Key<T> key) const;

  /**
   * Access style constants stored in the StyleProvider.
   *
   * @param key  The key of the value to retrieve. If this StyleProvider does
   *             not contain a mapping for the given key {@code NULL} is
   *             returned.
   */
  template<class T> T get(const Key<T> key);

};

#endif /* STYLE_PROVIDER_H */
