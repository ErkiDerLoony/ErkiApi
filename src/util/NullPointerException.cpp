#include "NullPointerException.hpp"

NullPointerException::NullPointerException() : mText(NULL) {}

NullPointerException::NullPointerException(QString& text) : mText(text) {}

NullPointerException::~NullPointerException() throw() {}

QString NullPointerException::text() {
  return mText;
}
