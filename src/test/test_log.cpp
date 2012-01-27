#include "log.hpp"

using namespace erki;

int main(int argc, char** argv) {
  log::set_use_colour(true);
  log::level = log::INFO;
  LOG_INFO("(0) The numbers in brackets should run from 0 to 5 without "
           << "leaving one out.");
  log::print_date = true;
  LOG_INFO("(1) The date should be printed in this log entry.");
  log::print_date = false;
  LOG_INFO("(2) But NOT in this one.");
  log::level = log::ERROR;
  LOG_WARNING("(!) This warning should NOT be displayed!");
  LOG_ERROR("(3) This error should be displayed.");
  log::level = log::NONE;
  LOG_ERROR("(!) This should also NOT be displayed!");
  log::level = log::INFO;
  LOG_INFO("(4) Log level was reset to test file specific logging.");
  LOG_ERROR("(5) This error should again be displayed.");
  log::set_level_for_file(log::NONE, "test_log.cpp");
  LOG_ERROR("(!) But this one NOT!");
}
