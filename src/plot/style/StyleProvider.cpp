#include <QColor>

#include "StyleProvider.hpp"
#include "Key.hpp"
#include "Object.hpp"
#include "Colour.hpp"

StyleProvider::StyleProvider() {
  Key<Colour> key(BACKGROUND);
  Colour value(QColor(127, 200, 255));
  std::pair<Key<Colour>, Colour> pair = std::make_pair(key, value);
  add(key, value);
}

StyleProvider::~StyleProvider() {}

template<typename T> void StyleProvider::add(Key<T> key, T value) {
  values.insert(std::make_pair(key, value));
}

template<typename T> void StyleProvider::add(std::pair<Key<T>, T> pair) {
  values.insert(pair);
}

template<typename T> bool StyleProvider::contains(Key<T> key) {
  return (values.find(key) != values.end());
}
