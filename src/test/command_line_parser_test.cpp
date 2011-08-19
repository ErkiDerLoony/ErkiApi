#include "command_line_parser.hpp"
#include "log.hpp"

int main(int argc, char** argv) {
  log::set_level_for_file(log::DEBUG, "command_line_parser.cpp");
  command_line_parser args(argc, argv);
  log::info << "Recognized switches:";
  std::map<std::string, std::string>::const_iterator it;

  for (it = args.begin(); it != args.end(); it++) {
    std::pair<std::string, std::string> val = *it;
    log::info << val.first << " -> " << val.second;
  }
}
