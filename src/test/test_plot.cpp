#include "gl/frame.hpp"

#include <string>

int main(int argc, char** argv) {
  erki::gl::frame f(std::string("Plot test"));
  f.show();

  return 0;
}
