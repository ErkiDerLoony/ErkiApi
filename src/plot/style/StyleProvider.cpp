#include <QColor>

#include "StyleProvider.hpp"
#include "Object.hpp"
#include "Colour.hpp"

StyleProvider::StyleProvider() {
  add(Key<Colour>(BACKGROUND), Colour(QColor(255,255,255)));
}

StyleProvider::~StyleProvider() {}

template<class T> void StyleProvider::add(const Key<T> key, const T value) {
  values[key] = value;
}

template<class T> bool StyleProvider::contains (const Key<T> key) const {
  return (values.find(key) != values.end());
}

template<class T> T StyleProvider::get(const Key<T> key) {
  return values[key];
}

template<> Colour StyleProvider::get(Key<Colour> key) {
  return reinterpret_cast<Colour>(values[key]);
}
