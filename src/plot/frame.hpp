#ifndef FRAME_HPP
#define FRAME_HPP

class frame {

public:
  frame(int argc, char** argv);
  virtual ~frame() {};

  void render();

  void update(int);

  void show();

};

#endif /* FRAME_HPP */
