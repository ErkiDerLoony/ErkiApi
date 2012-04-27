#include "null_pointer_exception.hpp"

null_pointer_exception::null_pointer_exception() : m_text(NULL) {}

null_pointer_exception::null_pointer_exception(std::string text)
  : m_text(text) {}

null_pointer_exception::~null_pointer_exception() throw() {}

std::string null_pointer_exception::text() {
  return m_text;
}
