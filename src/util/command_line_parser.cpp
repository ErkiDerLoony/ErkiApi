#include "command_line_parser.hpp"
#include "log.hpp"

#include <iostream>
#include <stdarg.h>

command_line_parser::command_line_parser(int argc, char** argv) {
  LOG_DEBUG("Parsing " << (argc - 1) << " options.");

  for (int i = 1; i < argc; i++) {
    LOG_DEBUG("Parsing option no. " << i << ".");
    std::string arg = argv[i];
    LOG_DEBUG("Option is " << arg);
    if (arg == "--") continue;

    if (arg.substr(0, 2) == "--") {
      // Parse long options.
      LOG_DEBUG("Recognized a long option.");
      arg = arg.substr(2);
      size_t found = arg.find("=");

      if (found != std::string::npos) {
        std::string key = arg.substr(0, found);
        std::string val = arg.substr(found + 1);
        m_args.insert(std::pair<std::string, std::string>(key, val));
      } else {

        if (i + 1 == argc || std::string(argv[i + 1]).substr(0, 1) == "-" ||
            std::string(argv[i + 1]) == "--") {
          m_args.insert(std::pair<std::string, std::string>(arg, ""));
        } else {
          m_args.insert(std::pair<std::string, std::string>(arg, argv[i + 1]));
          i++;
        }
      }

    } else if (arg.substr(0, 1) == "-") {
      // Parse short options.
      LOG_DEBUG("Recognized a short option.");
      arg = arg.substr(1);

      if (arg.size() > 1) {
        std::string key = arg.substr(0, 1);
        std::string val = arg.substr(1);
        m_args.insert(std::pair<std::string, std::string>(key, val));
      } else {

        if (i + 1 == argc || std::string(argv[i + 1]).substr(0, 1) == "-" ||
            std::string(argv[i + 1]) == "--") {
          m_args.insert(std::pair<std::string, std::string>(arg, ""));
        } else {
          m_args.insert(std::pair<std::string, std::string>(arg, argv[i + 1]));
          i++;
        }
      }
    } else {
      // Parse final list of strings (e.g. filenames).
      LOG_DEBUG("Parsing final list of strings.");

      for (int j = i; j < argc; j++) {
        LOG_DEBUG("Pushing " << argv[j] << ".");
        m_list.push_back(argv[j]);
      }

      break;
    }
  }
}

bool command_line_parser::contains(int nr, ...) {
  va_list vl;
  va_start(vl, nr);

  for (int i = 0; i < nr; i++) {
    std::string key = std::string(va_arg(vl, char*));
    while (key.substr(0, 1) == "-") key = key.substr(1);

    if (m_args.find(key) != m_args.end()) {
      va_end(vl);
      return true;
    }
  }

  return false;
}

std::vector<std::string> command_line_parser::list() {
  LOG_DEBUG("Accessing list (has " << m_list.size() << " entries).");
  return m_list;
}

std::map<std::string,
         std::string>::const_iterator command_line_parser::begin() {
  return m_args.begin();
}

std::map<std::string, std::string>::const_iterator command_line_parser::end() {
  return m_args.end();
}
