#include <QColor>

#include "StyleProvider.hpp"
#include "Object.hpp"
#include "Colour.hpp"

StyleProvider::StyleProvider() {
  Key<Colour> key(BACKGROUND);
  Colour value(QColor(127, 200, 255));
  add(key, value);
}

StyleProvider::~StyleProvider() {}

template<typename T> void StyleProvider::add(Key<T> key, T value) {
  values.insert(std::make_pair(key, value));
}

template<typename T> bool StyleProvider::contains(Key<T> key) {
  return (values.find(key) != values.end());
}
