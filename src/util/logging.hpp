#ifndef LOGGING_HPP
#define LOGGING_HPP

#include <string>

class log {

public:

  static log info;

  enum level {
    INFO
  };

  log(level);

  void operator<<(std::string text);

private:

  level lvl;

  static std::ostream& output;

};

#endif /* LOGGING_HPP */
