#include <iostream>
#include <map>

#include "Object.hpp"

enum Keys {
  BACKGROUND
};

template<class T> class Key {

  Keys mId;

public:
  Key(Keys id) : mId(id) {}
  virtual ~Key() {}

  Keys get() { return mId; }

  template<class O> operator Key<O>() { return Key<O>(mId); }
  bool operator<(Key<T> other) { return mId < other.mId; }

};

void add(Key<Object> key, Object value) {
  std::map<Key<Object>, Object> values;
  values.insert(std::make_pair(key, value));
}

class Colour : public Object {

};

int main() {
  Key<Colour> key(BACKGROUND);
  Colour value;
  add(key, value);
}
