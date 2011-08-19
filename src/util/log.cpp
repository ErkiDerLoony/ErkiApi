#include <string>
#include <iostream>
#include <time.h>

#include "log.hpp"

std::ostream& log::output = std::cout;

bool log::print_date = false;

log::loglevel log::level = log::INFO;

std::map<std::string, log::loglevel> log::mapping;

std::string log::format_file(std::string filename) {
  int pos = filename.find_last_of("/");
  filename = filename.substr(pos + 1);
  return filename;
}

bool log::is_loggable(log::loglevel level, std::string filename) {

  if (mapping.find(filename) != mapping.end()) {
    if (level < mapping[filename]) return false;
  } else {
    if (level < log::level) return false;
  }

  return true;
}

std::string log::format_time() {
  time_t rawtime;
  struct tm *timeinfo;
  char buffer[80];
  time(&rawtime);
  timeinfo = localtime(&rawtime);
  if (log::print_date)
    strftime(buffer, 80, "%d.%m.%Y %H:%M:%S,", timeinfo);
  else
    strftime(buffer, 80, "%H:%M:%S", timeinfo);
  return std::string(buffer);
}

std::string log::format_modifier(log::loglevel mod) {

  switch (mod) {
  case DEBUG:
    return "DEBUG";
  case INFO:
    return "INFO";
  case WARNING:
    return "WARNING";
  case ERROR:
    return "ERROR";
  default:
    return "UNKNOWN";
  }
}

void log::set_level_for_file(log::loglevel level, std::string file) {
  log::mapping.insert(std::pair<std::string, log::loglevel>(file, level));
}
