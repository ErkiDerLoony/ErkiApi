#include "Log.hpp"

int main(int argc, char** argv) {
  Log::printDate = true;
  log_info("The date should be printed in this log entry.");
  Log::printDate = false;
  log_info("But not in this one.");
  Log::level = Log::ERROR;
  log_warning("This warning should not be displayed.");
  log_error("But this error should.");
  Log::level = Log::NONE;
  log_error("This should also not be displayed.");
}
