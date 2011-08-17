#include <string>
#include <iostream>
#include <time.h>

#include "log.hpp"

std::ostream& log::output = std::cout;

bool log::print_date = false;

log::loglevel log::level = log::INFO;

std::map<std::string, log::loglevel> log::mapping;

void log::log(log::loglevel modifier, int line, std::string file,
              std::string function, std::string text) {

  // Create better filename representation.
  int pos = file.find_last_of("/");
  file = file.substr(pos + 1);

  if (mapping.find(file) != mapping.end()) {
    if (modifier < mapping[file]) return;
  } else {
    if (modifier < log::level) return;
  }

  // Construct timing information.
  time_t rawtime;
  struct tm *timeinfo;
  char buffer[80];
  time(&rawtime);
  timeinfo = localtime(&rawtime);
  if (log::print_date)
    strftime(buffer, 80, "%d.%m.%Y %H:%M:%S,", timeinfo);
  else
    strftime(buffer, 80, "%H:%M:%S", timeinfo);

  std::string mod;

  switch (modifier) {
  case DEBUG:
    mod = "DEBUG";
    break;
  case INFO:
    mod = "INFO";
    break;
  case WARNING:
    mod = "WARNING";
    break;
  case ERROR:
    mod = "ERROR";
    break;
  default:
    mod = "UNKNOWN";
  }

  // Output everything.
  log::output << "[" << buffer << " " << file << "::" << function << "(" <<
    line << ")] " << mod << ": " << text << std::endl;
}

void log::set_level_for_file(log::loglevel level, std::string file) {
  log::mapping.insert(std::pair<std::string, log::loglevel>(file, level));
}
