#ifndef LOG_H
#define LOG_H

#include <string>
#include <iostream>
#include <map>

#define LOG(l,m,t)                                                    \
  l "[" << log::time() << " " << __FILE__ << "::" << __FUNCTION__     \
  << "(" << __LINE__ << ")] " << m << " " t

#define LOG_DEBUG(t)                            \
  LOG(log::debug, "DEBUG", t)

#define LOG_INFO(t)                             \
  LOG(log::info, "INFO", t)

#define LOG_WARNING(t)                          \
  LOG(log::warning, "WARNING", t)

#define LOG_ERROR(t)                            \
  LOG(log::error, "ERROR", t)

/**
 * This class represents a log utility. It supports multiple log levels and
 * adjustment of the level on a per-file basis.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class log {

public:

  /** This enum contains all possible log levels. */
  enum loglevel {
    DEBUG, INFO, WARNING, ERROR, NONE
  };

private:

  /**
   * This class uses the {@code std::ostream} of a {@link log} to output logging
   * information if the log level fits.
   */
  class log_ostream {

  private:

    /** This log level of this stream. */
    log::loglevel m_level;

    /** The method that actually logs stuff the the {@link m_output}. */
    /*    void real_log(log::loglevel modifier, int line, std::string file,
          std::string function, std::string text);*/

  public:

    /**
     * Create a new log_ostream.
     *
     * @param level  The log level of the new stream.
     */
    log_ostream(log::loglevel level);

    /** Do cleanup if necessary. */
    virtual ~log_ostream();

    log_ostream& operator<<(const char*);
    log_ostream& operator<<(const std::string&);

  };

  /** Allow log_ostream to use private members. */
  friend class log_ostream;

  /** The actual output all logging information is printed to. */
  static std::ostream& m_output;

  /** Indicates whether or not the date shall be included in the log output. */
  static bool m_print_date;

  /**
   * The current global log level which is used if no specific log level is
   * defined for a file.
   */
  static loglevel m_level;

  /** The mapping of specific log levels for some files. */
  static std::map<std::string, loglevel> m_mapping;

public:

  /**
   * This stream is used to log debug information. Do NOT use it directly but
   * rather use the {@link LOG_DEBUG} macro. The debug messages are only
   * actually logged if the log level for the file that contains the log
   * instruction is sufficiently high.
   */
  static log_ostream debug;

  /**
   * This stream is used to log info messages. Do NOT use it directly but rather
   * use the {@link LOG_INFO} macro. The info messages are only actually logged
   * if the log level for the file that contains the log instruction is
   * sufficiently high.
   */
  static log_ostream info;

  /**
   * This stream is used to log warning messages. Do NOT use it directly but
   * rather use the {@link LOG_WARNING} macro. The warning messages are only
   * actually logged if the log level for the file that contains the log
   * instruction is sufficiently high.
   */
  static log_ostream warning;

  /**
   * This stream is used to log error messages. Do NOT use it directly but
   * rather use the {@link LOG_ERROR} macro. The error messages are only
   * actually logged if the log level for the file that contains the log
   * instruction is sufficiently high.
   */
  static log_ostream error;

  /**
   * Change the specific log level for some file.
   *
   * @param level  The new log level for the given file.
   * @param file   The filename for which the log level shall be changed.
   */
  static void set_level_for_file(loglevel level, std::string file);

  /**
   * Change the global log level which is used if no specific log level is
   * specified for some file.
   *
   * @param level  The new global log level.
   */
  static void set_level(loglevel level);

  /**
   * Change the actual log output.
   *
   * @param output  The new stream to which all log output is printed.
   */
  static void set_output(std::ostream& output);

  /**
   * Change the flag that indicates whether the date shall be included in the
   * log output.
   *
   * @param print_date  If this is {@code true} the date is included, otherwise
   *                    it is not.
   */
  static void set_print_date(bool print_date);

};

#endif /* LOG_H */
