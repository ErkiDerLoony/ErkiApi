/* Forward declaration. */
class StyleProvider;

/**
 * This class is used to retain the type of style constant stored in an instance
 * of {@link StyleProvider}. The actual type is not used when comparing two keys
 * so make sure the used identifier (see {@link Keys}) is unique.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
template <typename T> class Key {

public:
  Key(Keys key, T value);
  virtual ~Key() {};

private:
  Keys mKey;
  T mValue;

};
