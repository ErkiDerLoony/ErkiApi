#ifndef FRAME_HPP
#define FRAME_HPP

#include <string>

namespace erki {

namespace gl {

class frame {

public:
  frame(std::string title = "Frame");
  virtual ~frame() {};

  void render();

  void update();

  void show();

private:

  static bool gl_initialized;

}; /* class frame */

} /* namespace gl */

} /* namespace erki */

#endif /* FRAME_HPP */
