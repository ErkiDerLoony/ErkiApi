#include <iostream>

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

};

int main() {
  Key<int> key(BACKGROUND);
  std::cout << key.get() << std::endl;
  Key<double> dkey = key;
  std::cout << dkey.get() << std::endl;
}
