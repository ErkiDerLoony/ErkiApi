#ifndef COMMAND_LINE_PARSER_H
#define COMMAND_LINE_PARSER_H

#include <string>
#include <map>
#include <vector>

/**
 * This class takes command line arguments as passed to the main method and
 * parses them into a more comfortable map<string, string>.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
// TODO: Use getopt or something.
class command_line_parser {

  std::map<std::string, std::string> m_args;

  std::vector<std::string> m_list;

public:

  /**
   * Create a new command_line_parser.
   *
   * @param argc  The number of arguments that are contained in the argument
   *              array.
   * @param argv  The argument array as provided by {@code main} (i.e. the first
   *              entry contains the program’s name).
   */
  command_line_parser(int argc, char** argv);

  /**
   * Checks whether or not some keys are contained in the internal mapping of
   * this class.
   *
   * @param keys  The keys to check.
   */
  bool contains(int nr, ...);

  /**
   * Access a const iterator that initially points to the beginning of the
   * internal key-value mapping. This allows for iteration over all switches
   * contained in this command_line_parser.
   *
   * @return  The begin iterator of the underlying map.
   */
  std::map<std::string, std::string>::const_iterator begin();

  /**
   * Access a const iterator that points to the end of the internal key-value
   * mapping. This allows for iteration over all switches contained in this
   * command_line_parser.
   *
   * @return  The end iterator of the underlying map.
   */
  std::map<std::string, std::string>::const_iterator end();

  /**
   * Access a copy of the internal list of values that were supplied after the
   * option switches on the command line.
   *
   * @return  A list of finally supplied strings (e.g. file names).
   */
  std::vector<std::string> list();

  //std::string peek(std::string keys);

  //std::string pop(std::string keys);

};

#endif // COMMAND_LINE_PARSER_H
