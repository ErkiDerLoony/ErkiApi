#ifndef LCARS_FRAME
#define LCARS_FRAME

#include <QWidget>
#include <QFont>

#include "NullPointerException.hpp"

class LcarsFrame : public QWidget {

Q_OBJECT

public:

  /**
   * Create a new LcarsFrame.
   *
   * @param title  the title of the new frame
   */
  LcarsFrame(QString& title);

  /** Delete this frame’s content before it is destroyed itself. */
  virtual ~LcarsFrame();

  /**
   * Access the widget that acts as a container for all the content of this
   * frame.
   *
   * @returns  the content of this frame
   */
  QWidget* content();

  /**
   * Change the content of this frame.
   *
   * @param content  the new widget that shall act as the container for the
   *                 content of this frame
   * @throws NullPointerException  if the given widget is NULL
   */
  void setContent(QWidget* content) throw(NullPointerException);

protected:
  void paintEvent(QPaintEvent* event);
  void resizeEvent(QResizeEvent* event);

private:
  QWidget* mContent;
  QString title;
  static const float OFFSET = 5.0;
  static const float DIAMETER = 30.0;
  static const float BAR_WIDTH = 130.0;

};

#endif // LCARS_FRAME
