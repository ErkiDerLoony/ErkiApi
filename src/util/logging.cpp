#include "logging.hpp"

#include <iostream>

log log::info = log(log::INFO);

log::log(level lvl) {
  this->lvl = lvl;
}

void log::operator<<(std::string text) {
  log::output << text;
}
