#include "log.hpp"

log::log_ostream::log_ostream(log::loglevel level) : m_level(level) {}

log::log_ostream::~log_ostream() {}

/*
void log::log_ostream::real_log(log::loglevel modifier, std::string text) {

  // Create better filename representation.
  int pos = file.find_last_of("/");
  file = file.substr(pos + 1);

  if (log::m_mapping.find(file) != log::m_mapping.end()) {
    if (modifier < log::m_mapping[file]) return;
  } else {
    if (modifier < log::m_level) return;
  }

  // Construct timing information.
  time_t rawtime;
  struct tm *timeinfo;
  char buffer[80];
  time(&rawtime);
  timeinfo = localtime(&rawtime);
  if (log::m_print_date)
    strftime(buffer, 80, "%d.%m.%Y %H:%M:%S,", timeinfo);
  else
    strftime(buffer, 80, "%H:%M:%S", timeinfo);

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
  log::m_output << "[" << buffer << " " << file << "::" << function << "(" <<
    line << ")] " << mod << ": " << text << std::endl;
}
*/

log::log_ostream& log::log_ostream::operator<<(const char* text) {
  return *this;
}

log::log_ostream& log::log_ostream::operator<<(const std::string& text) {
  real_log(m_level, text);
  return *this;
}

std::ostream& log::m_output = std::cout;
bool log::m_print_date = false;
log::loglevel log::m_level = INFO;
std::map<std::string, log::loglevel> log::m_mapping =
                   std::map<std::string, log::loglevel>();

log::log_ostream log::debug = log::log_ostream(DEBUG);
log::log_ostream log::info = log::log_ostream(INFO);
log::log_ostream log::warning = log::log_ostream(warning);
log::log_ostream log::error = log::log_ostream(error);

void log::set_level_for_file(log::loglevel level, std::string file) {
  log::log::m_mapping.insert(std::pair<std::string,
                                       log::loglevel>(file, level));
}

void log::set_level(log::loglevel level) {
  log::m_level = level;
}

void log::set_output(std::ostream& output) {
  //log::error << "This feature is not yet implemented.";
}

void log::set_print_date(bool print_date) {
  log::m_print_date = print_date;
}
