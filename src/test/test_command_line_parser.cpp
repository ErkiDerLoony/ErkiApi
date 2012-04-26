#include "util/command_line_parser.hpp"
#include "util/log.hpp"

#include <sstream>

int main(int argc, char** argv) {
  //log::set_level_for_file(log::DEBUG, "command_line_parser.cpp");
  command_line_parser args(argc, argv);
  LOG_INFO("Recognized switches: ");
  std::map<std::string, std::string>::const_iterator it;

  for (it = args.begin(); it != args.end(); it++) {
    std::pair<std::string, std::string> val = *it;
    LOG_INFO(val.first << " -> " << val.second);
  }

  std::stringstream s;
  std::vector<std::string> list = args.list();

  for (unsigned int i = 0; i < list.size(); i++) {

    if (i < list.size() - 1) {
      s << list[i] << ", ";
    } else {
      s << list[i];
    }
  }

  LOG_INFO("Recognized list (" << list.size() << " elements): "
           << s.str());
}
