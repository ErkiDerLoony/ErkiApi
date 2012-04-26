#ifndef DRAWER_HPP
#define DRAWER_HPP

/**
 * This class is an interface for all drawers that want to display something
 * in a {@link plot2d}.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
class drawer {

public:

  virtual ~drawer() {};

  /**
   * This method is called by Plot2d to trigger a repaint of the contents of
   * this drawer onto the plot.
   */
  virtual void draw() = 0;

};

#endif // DRAWER_HPP
