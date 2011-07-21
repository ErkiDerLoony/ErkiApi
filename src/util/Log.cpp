#include <string>
#include <iostream>
#include <time.h>

#include "Log.hpp"

void Log::log(Log::Level modifier, int line, std::string file,
              std::string function, std::string text) {

  if (modifier < level) return;

  // Construct timing information.
  time_t rawtime;
  struct tm *timeinfo;
  char buffer[80];
  time(&rawtime);
  timeinfo = localtime(&rawtime);
  if (printDate)
    strftime(buffer, 80, "%d.%m.%Y %H:%M:%S,", timeinfo);
  else
    strftime(buffer, 80, "%H:%M:%S", timeinfo);

  // Create better filename representation.
  int pos = file.find_last_of("/");
  file = file.substr(pos + 1);
  //pos = file.find_last_of(".");
  //file = file.substr(0, pos);

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
  std::cout << "[" << buffer << " " << function << "(" << file << ":" <<
    line << ")] " << mod << ": " << text << std::endl;
}
