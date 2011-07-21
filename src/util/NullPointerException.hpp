#ifndef NULL_POINTER_EXCEPTION_H
#define NULL_POINTER_EXCEPTION_H

#include <exception>
#include <QString>

/**
 * This class models an exception which indicates that a null pointer occurred.
 * It may additionally contain a textual explanation of the error.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class NullPointerException : public std::exception {

public:

  /**
   * Create a new NullPointerException.
   *
   * @param text  The error message of the new exception.
   */
  NullPointerException(QString* text);

  /**
   * Create a new NullPointerException without a specific textual explanation.
   */
  NullPointerException();

  /** Do cleanup if necessary. */
  virtual ~NullPointerException() throw();

  /**
   * Access the error message stored with this exception.
   *
   * @returns  this exception’s error message
   */
  QString* text();

private:
  QString* mText;

};

#endif // NULL_POINTER_EXCEPTION_H
