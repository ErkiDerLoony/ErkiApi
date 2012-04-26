#include <string>
#include <iostream>
#include <time.h>

#include "log.hpp"

using namespace erki::util;

namespace erki { namespace util {

std::ostream& output = std::cout;

bool print_date = false;

bool use_colour = false;

loglevel level = INFO;

std::map<std::string, loglevel> mapping;

std::map<loglevel, ansi_colour> colours = {
  std::pair<loglevel, ansi_colour>(ERROR, BRIGHT_RED),
  std::pair<loglevel, ansi_colour>(WARNING, BRIGHT_YELLOW),
  //std::pair<loglevel, ansi_colour>(INFO, BRIGHT_GREEN),
  std::pair<loglevel, ansi_colour>(DEBUG, BRIGHT_BLACK),
  std::pair<loglevel, ansi_colour>(FINE_DEBUG, BRIGHT_BLACK),
  std::pair<loglevel, ansi_colour>(FINER_DEBUG, BRIGHT_BLACK),
  std::pair<loglevel, ansi_colour>(FINEST_DEBUG, BRIGHT_BLACK)
};

}}

std::string erki::util::format_file(std::string filename) {
  int pos = filename.find_last_of("/");
  filename = filename.substr(pos + 1);
  return filename;
}

bool erki::util::is_loggable(loglevel level, std::string filename) {

  if (erki::util::mapping.find(filename) != erki::util::mapping.end()) {
    if (level < erki::util::mapping[filename]) return false;
  } else {
    if (level < erki::util::level) return false;
  }

  return true;
}

std::string erki::util::format_time() {
  time_t rawtime;
  struct tm *timeinfo;
  char buffer[80];
  time(&rawtime);
  timeinfo = localtime(&rawtime);
  if (print_date)
    strftime(buffer, 80, "%d.%m.%Y %H:%M:%S,", timeinfo);
  else
    strftime(buffer, 80, "%H:%M:%S", timeinfo);
  return std::string(buffer);
}

std::string erki::util::format_modifier(loglevel mod) {

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

void erki::util::set_level_for_file(loglevel level, std::string file) {
  mapping.insert(std::pair<std::string, loglevel>(file, level));
}

void erki::util::set_colour(loglevel level, ansi_colour colour) {
  colours.insert(std::pair<loglevel,
                                ansi_colour>(level, colour));
}

void erki::util::set_use_colour(bool use_colour) {
  erki::util::use_colour = use_colour;
}

std::string erki::util::start_colour(loglevel level) {

  if (use_colour) {
    ansi_colour colour = colours[level];

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

std::string erki::util::end_colour() {

  if (use_colour) {
    return "\e[m";
  } else {
    return "";
  }
}
