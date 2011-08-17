#include "log.hpp"

int main(int argc, char** argv) {
  log::level = log::INFO;
  log::print_date = true;
  log_info("The date should be printed in this log entry.");
  log::print_date = false;
  log_info("But NOT in this one.");
  log::level = log::ERROR;
  log_warning("This warning should NOT be displayed!");
  log_error("This error should be displayed.");
  log::level = log::NONE;
  log_error("This should also NOT be displayed!");
  log::level = log::INFO;
  log_info("Log level was reset to test file specific logging.");
  log_error("This error should again be displayed.");
  log::set_level_for_file(log::NONE, "log_test.cpp");
  log_error("But this one NOT!");
}
