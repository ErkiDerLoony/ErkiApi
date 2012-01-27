#include "style_provider.hpp"
#include "object.hpp"
#include "colour.hpp"

style_provider::style_provider() {
  add(key<colour>(BACKGROUND), new colour(255,255,255));
}

style_provider::~style_provider() {
  std::map<key<object>, object*>::iterator it;
  for (it = values.begin(); it != values.end(); it++) {
    delete values[it->first];
  }
}

template<class T> void style_provider::add(const key<T> key, T* value) {
  if (contains(key)) delete values[key];
  values[key] = value;
}

template<class T> bool style_provider::contains (const key<T> key) const {
  return (values.find(key) != values.end());
}

template<class T> T* style_provider::get(const key<T> key) {
  return dynamic_cast<T*>(values[key]);
}

// TODO: Why do I have to specialize the template function for each type?
template<> colour* style_provider::get(const key<colour> key) {
  return dynamic_cast<colour*>(values[key]);
}
