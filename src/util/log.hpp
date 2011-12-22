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
    log::output << log::start_colour(m) << "[" << log::format_time()    \
                << " " << log::format_file(__FILE__) << "::"            \
                << __FUNCTION__ << "(" << __LINE__ << ")] "             \
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

/**
 * This macro logs fine debug information if the log level is sufficiently
 * high.
 */
#define LOG_FINE_DEBUG \
  LOG(log::FINE_DEBUG)

/**
 * This macro logs finer debug information if the log level is sufficiently
 * high.
 */
#define LOG_FINER_DEBUG \
  LOG(log::FINER_DEBUG)

/**
 * This macro logs finest debug information if the log level is sufficiently
 * high.
 */
#define LOG_FINEST_DEBUG \
  LOG(log::FINEST_DEBUG)

namespace log {

  /** This enum defines the possible log levels. */
  enum loglevel {
    FINEST_DEBUG, FINER_DEBUG, FINE_DEBUG, DEBUG, INFO, WARNING, ERROR, NONE
  };

  /**
   * This enum defines constants for the colours that can be achieved by using
   * ansi control characters and which may be used to colour the log output.
   */
  enum ansi_colour {
    NO_COLOUR,

    BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE,

    BRIGHT_BLACK, BRIGHT_RED, BRIGHT_GREEN, BRIGHT_YELLOW, BRIGHT_BLUE,
    BRIGHT_MAGENTA, BRIGHT_CYAN, BRIGHT_WHITE
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
   * This mapping contains the colours that are used when printing log messages
   * if colouring is enabled.
   */
  extern std::map<loglevel, ansi_colour> colours;

  /**
   * Set a special log level for some file. This may also be done by inserting
   * the log level/filename pair into {@link mapping}.
   *
   * @param level  The log level that shall be set.
   * @param file   The file the log level shall be changed for.
   */
  void set_level_for_file(loglevel level, std::string file);

  /**
   * Change the colour that is used to print messages of a given log level. Note
   * that colouring has to be enabled for this to have any effect.
   *
   * @param level   The log level whose colour shall be changed.
   * @param colour  The new colour for the given log level.
   */
  void set_colour(loglevel level, ansi_colour colour);

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

  /**
   * If colouring is enabled for the given log level return a string that
   * contains the start sequence for the correct colour.
   */
  std::string start_colour(loglevel level);

  /**
   * If colouring is enabled for the given log level return a string that
   * contains the end sequence for colouring.
   */
  std::string end_colour(loglevel level);

}

#endif /* LOG_H */
