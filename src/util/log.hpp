#ifndef LOG_H
#define LOG_H

#include <string>
#include <iostream>
#include <map>

#define log_error(t) (log::log(log::ERROR, __LINE__,            \
                               __FILE__, __FUNCTION__, t))

#define log_warning(t) (log::log(log::WARNING, __LINE__,        \
                                 __FILE__, __FUNCTION__, t))

#define log_info(t) (log::log(log::INFO, __LINE__,              \
                              __FILE__, __FUNCTION__, t))

#define log_debug(t) (log::log(log::DEBUG, __LINE__,            \
                               __FILE__, __FUNCTION__, t))

namespace log {

  enum loglevel {
    DEBUG, INFO, WARNING, ERROR, NONE
  };

  extern std::ostream& output;

  extern bool print_date;

  extern loglevel level;

  extern std::map<std::string, loglevel> mapping;

  void log(loglevel modifier, int line, std::string file,
           std::string function, std::string text);

  void set_level_for_file(loglevel level, std::string file);
}

#endif /* LOG_H */
