#ifndef LCARS_FRAME
#define LCARS_FRAME

#include <QWidget>
#include <QFont>

#include "null_pointer_exception.hpp"

/**
 * This class represents a frame with special LCARS decoration and buttons. The
 * frame contains a special widget that holds the content of the frame and which
 * can be changed by calling setContent(QWidget*).
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
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
   * @param content  The new widget that shall act as the container for the
   *                 content of this frame.
   * @throws null_pointer_exception  if the given widget is 0.
   */
  void setContent(QWidget* content) throw(null_pointer_exception);

  /**
   * Override QWidget::setLayout(QLayout*) and redirect the call to #content().
   *
   * @param layout  The layout that will be displayed within the bounds of
   *                #content().
   */
  void setLayout(QLayout* layout);

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
