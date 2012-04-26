#include "util/log.hpp"

using namespace erki::util;

int main(int argc, char** argv) {
  set_use_colour(true);
  level = INFO;
  LOG_INFO("(0) The numbers in brackets should run from 0 to 5 without "
           << "leaving one out.");
  print_date = true;
  LOG_INFO("(1) The date should be printed in this log entry.");
  print_date = false;
  LOG_INFO("(2) But NOT in this one.");
  level = ERROR;
  LOG_WARNING("(!) This warning should NOT be displayed!");
  LOG_ERROR("(3) This error should be displayed.");
  level = NONE;
  LOG_ERROR("(!) This should also NOT be displayed!");
  level = INFO;
  LOG_INFO("(4) Log level was reset to test file specific logging.");
  LOG_ERROR("(5) This error should again be displayed.");
  set_level_for_file(NONE, "test_log.cpp");
  LOG_ERROR("(!) But this one NOT!");
}
