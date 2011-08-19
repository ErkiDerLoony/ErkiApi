#include "log.hpp"

int main(int argc, char** argv) {
  log::set_level(log::INFO);
  log::set_print_date(true);
  log::info << "The date should be printed in this log entry.";
  log::set_print_date(false);
  log::info << "But NOT in this one.";
  log::set_level(log::ERROR);
  log::warning << "This warning should NOT be displayed!";
  log::error << "This error should be displayed.";
  log::set_level(log::NONE);
  log::error << "This should also NOT be displayed!";
  log::set_level(log::INFO);
  log::info << "Log level was reset to test file specific logging.";
  log::error << "This error should again be displayed.";
  log::set_level_for_file(log::NONE, "log_test.cpp");
  log::error << "But this one NOT!";
}
