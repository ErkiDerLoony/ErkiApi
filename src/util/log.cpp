#include <string>
#include <iostream>
#include <time.h>

#include "log.hpp"

using namespace erki;

std::ostream& log::output = std::cout;

bool log::print_date = false;

bool log::use_colour = false;

log::loglevel log::level = log::INFO;

std::map<std::string, log::loglevel> log::mapping;

std::map<log::loglevel, log::ansi_colour> log::colours = {
  std::pair<log::loglevel, log::ansi_colour>(ERROR, BRIGHT_RED),
  std::pair<log::loglevel, log::ansi_colour>(WARNING, BRIGHT_YELLOW),
  std::pair<log::loglevel, log::ansi_colour>(INFO, BRIGHT_GREEN),
  std::pair<log::loglevel, log::ansi_colour>(DEBUG, BRIGHT_BLACK),
  std::pair<log::loglevel, log::ansi_colour>(FINE_DEBUG, BRIGHT_BLACK),
  std::pair<log::loglevel, log::ansi_colour>(FINER_DEBUG, BRIGHT_BLACK),
  std::pair<log::loglevel, log::ansi_colour>(FINEST_DEBUG, BRIGHT_BLACK)
};

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

void log::set_colour(log::loglevel level, log::ansi_colour colour) {
  log::colours.insert(std::pair<log::loglevel,
                                log::ansi_colour>(level, colour));
}

void log::set_use_colour(bool use_colour) {
  log::use_colour = use_colour;
}

std::string log::start_colour(log::loglevel level) {

  if (log::use_colour) {
    ansi_colour colour = log::colours[level];

    switch (colour) {
    case BLACK:
      return "\e[30m";
    case RED:
      return "\e[31m";
    case GREEN:
      return "\e[32m";
    case YELLOW:
      return "\e[33m";
    case BLUE:
      return "\e[34m";
    case MAGENTA:
      return "\e[35m";
    case CYAN:
      return "\e[36m";
    case WHITE:
      return "\e[37m";
    case BRIGHT_BLACK:
      return "\e[90m";
    case BRIGHT_RED:
      return "\e[91m";
    case BRIGHT_GREEN:
      return "\e[92m";
    case BRIGHT_YELLOW:
      return "\e[93m";
    case BRIGHT_BLUE:
      return "\e[94m";
    case BRIGHT_MAGENTA:
      return "\e[95m";
    case BRIGHT_CYAN:
      return "\e[96m";
    case BRIGHT_WHITE: 
     return "\e[97m";
    default:
      return "";
    }

  } else {
    return "";
  }
}

std::string log::end_colour() {

  if (log::use_colour) {
    return "\e[m";
  } else {
    return "";
  }
}
