#ifndef LOG_H
#define LOG_H

#include <string>
#include <iostream>
#include <map>

/**
 * This macro is used internally by the other log macros and should not be
 * used explicitely.
 */
#define LOG(m)                                                          \
  if (log::is_loggable(m, log::format_file(__FILE__)))                  \
    log::output << "[" << log::format_time() << " "                     \
                << log::format_file(__FILE__) << "::" << __FUNCTION__   \
                << "(" << __LINE__ << ")] "                             \
                << log::format_modifier(m) << ": "                      \

/** This macro logs an error message if the log level is sufficiently high. */
#define LOG_ERROR \
  LOG(log::ERROR)

/** This macro logs a warning message if the log level is sufficiently high. */
#define LOG_WARNING \
  LOG(log::WARNING)

/** This macro logs an info message if the log level is sufficiently high. */
#define LOG_INFO \
  LOG(log::INFO)

/** This macro logs debug information if the log level is sufficiently high. */
#define LOG_DEBUG \
  LOG(log::DEBUG)

namespace log {

  /** This enum defines the possible log levels. */
  enum loglevel {
    DEBUG, INFO, WARNING, ERROR, NONE
  };

  /**
   * The output all log messages that are loggable under the given log level
   * conditions shall be printed to.
   */
  extern std::ostream& output;

  /**
   * This flag specifies whether or not the date shall be included in log
   * messages.
   */
  extern bool print_date;

  /** The current global log level. */
  extern loglevel level;

  /** The mapping that contains special log levels for some files. */
  extern std::map<std::string, loglevel> mapping;

  /**
   * Set a special log level for some file. This may also be done by inserting
   * the log level/filename pair into {@link mapping}.
   *
   * @param level  The log level that shall be set.
   * @param file   The file the log level shall be changed for.
   */
  void set_level_for_file(loglevel level, std::string file);

  /**
   * Check whether or not a log message shall actually be printed to the log
   * regarding the current log level and any special log level defined for the
   * file that contains the log instruction.
   *
   * @param level  The level of the message that shall be logged.
   * @param file   The file that contained the log instruction.
   */
  bool is_loggable(loglevel level, std::string file);

  /**
   * Format a log level for output.
   *
   * @param level  The log level to format.
   */
  std::string format_modifier(loglevel level);

  /** Format the current time for output in a log message. */
  std::string format_time();

  /** Format a filename for output in a log message. */
  std::string format_file(std::string filename);

}

#endif /* LOG_H */
