#include <map>

class Object {};

class Colour : public Object {};

enum Keys {
  BACKGROUND
};

template<class T> class Key {

  Keys mId;

public:
  Key(Keys id) : mId(id) {}
  virtual ~Key() {}

  Keys get() { return mId; }

  template<class O> operator Key<O>() const { return Key<O>(mId); }
  bool operator<(const Key<T> other) const { return mId < other.mId; }

};

std::map<Key<Object>, Object> values;

template<class T> void add(Key<T> key, T value) {
  values[key] = value;
}

template<class T> T get(Key<T> key) {
  return (T) (values[key]);
}

template<> Colour get(Key<Colour> key) {
  return reinterpret_cast<Colour>(values[key]);
}

int main() {
  Key<Colour> key(BACKGROUND);
  Colour value;
  add(key, value);
  Colour c = get(key);
}
