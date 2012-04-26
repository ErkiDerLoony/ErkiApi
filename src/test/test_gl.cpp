#include "gl/frame.hpp"
#include "util/log.hpp"

int main() {
  erki::util::level = erki::util::DEBUG;
  erki::util::use_colour = true;
  erki::gl::frame frame0;
  frame0.show();
}
