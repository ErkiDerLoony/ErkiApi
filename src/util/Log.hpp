#ifndef LOG_H
#define LOG_H

#include <string>
#include <iostream>

#define log_error(t) (Log::log(Log::ERROR, __LINE__,            \
                               __FILE__, __FUNCTION__, t))

#define log_warning(t) (Log::log(Log::WARNING, __LINE__,        \
                                 __FILE__, __FUNCTION__, t))

#define log_info(t) (Log::log(Log::INFO, __LINE__,              \
                              __FILE__, __FUNCTION__, t))

#define log_debug(t) (Log::log(Log::DEBUG, __LINE__,            \
                               __FILE__, __FUNCTION__, t))

namespace Log {

  enum Level {
    DEBUG, INFO, WARNING, ERROR, NONE
  };

  std::ostream& output = std::cout;

  bool printDate = false;

  Level level = INFO;

  void log(Level modifier, int line, std::string file, std::string function,
           std::string text);
}

#endif /* LOG_H */
