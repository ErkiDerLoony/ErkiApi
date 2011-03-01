#include "NullPointerException.hpp"

NullPointerException::NullPointerException() : mText((char*) NULL) {}

NullPointerException::NullPointerException(QString& text) : mText(text) {}

NullPointerException::~NullPointerException() throw() {}

QString NullPointerException::text() {
  return mText;
}
