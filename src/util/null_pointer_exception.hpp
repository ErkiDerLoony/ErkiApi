#ifndef NULL_POINTER_EXCEPTION_H
#define NULL_POINTER_EXCEPTION_H

#include <exception>
#include <string>

/**
 * This class models an exception which indicates that a null pointer occurred.
 * It may additionally contain a textual explanation of the error.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class null_pointer_exception : public std::exception {

public:

  /**
   * Create a new null_pointer_exception.
   *
   * @param text  The error message of the new exception.
   */
  null_pointer_exception(std::string text);

  /**
   * Create a new null_pointer_exception without a specific textual explanation.
   */
  null_pointer_exception();

  /** Do cleanup if necessary. */
  virtual ~null_pointer_exception() throw();

  /**
   * Access the error message stored with this exception.
   *
   * @returns  this exception’s error message
   */
  std::string text();

private:
  std::string m_text;

};

#endif // NULL_POINTER_EXCEPTION_H
