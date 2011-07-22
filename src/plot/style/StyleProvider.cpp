#include <QColor>

#include "StyleProvider.hpp"
#include "Object.hpp"
#include "Colour.hpp"

StyleProvider::StyleProvider() {
  add(Key<Colour>(BACKGROUND), new Colour(QColor(255,255,255)));
}

StyleProvider::~StyleProvider() {
  std::map<Key<Object>, Object*>::iterator it;
  for (it = values.begin(); it != values.end(); it++) {
    delete values[it->first];
  }
}

template<class T> void StyleProvider::add(const Key<T> key, T* value) {
  if (contains(key)) delete values[key];
  values[key] = value;
}

template<class T> bool StyleProvider::contains (const Key<T> key) const {
  return (values.find(key) != values.end());
}

template<class T> T* StyleProvider::get(const Key<T> key) {
  return dynamic_cast<T*>(values[key]);
}

// TODO: Why do I have to specialize the template function for each type?
template<> Colour* StyleProvider::get(const Key<Colour> key) {
  return dynamic_cast<Colour*>(values[key]);
}
