#ifndef STYLE_PROVIDER_HPP
#define STYLE_PROVIDER_HPP

#include <map>

/**
 * This enum is used to uniquely identify style constants stored in a
 * StyleProvider.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
enum keys {

  /** The background colour for the plot as an instance of QColor. */
  BACKGROUND

};

/**
 * This class is used to retain the type of style constant stored in an instance
 * of {@link StyleProvider}. The actual type is not used when comparing two keys
 * so make sure the used identifier (see {@link keys}) is unique.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
template<class T> class key {

  keys mId;

public:
  key(keys id) : id(id) {}
  virtual ~Key() {}

  template<class O> operator key<O>() const { return key<O>(mId); }

  bool operator<(const key<T> other) const { return id < other.id; }

};

/**
 * This class is used to store style constants that define the look of a
 * {@link plot2d}.
 *
 * @autor Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class style_provider {

  /**
   * This map stores the internal mapping of {@link Key} instances to the
   * according values.
   */
  std::map<key<object>, object*> values;

public:

  /**
   * Create a new style_provider that contains default values for all constants
   * necessary for {@link plot2d}.
   */
  style_provider();

  /** Delete this style_provider and clean up all contained values. */
  virtual ~style_provider();

  /**
   * Add a new value to this style_provider.
   *
   * @param key    The unique key under which the new value will be stored.
   * @param value  The new value.
   */
  template<class T> void add(const key<T> key, T* value);

  /**
   * Check whether a value is stored under some key.
   *
   * @param key  The key to check.
   */
  template<class T> bool contains(const key<T> key) const;

  /**
   * Access style constants stored in the style_provider.
   *
   * @param key  The key of the value to retrieve. If this style_provider does
   *             not contain a mapping for the given key {@code NULL} is
   *             returned.
   */
  template<class T> T* get(const key<T> key);

};

#endif /* STYLE_PROVIDER_HPP */
