#ifndef LCARS_FRAME
#define LCARS_FRAME

#include <QWidget>

#include "NullPointerException.hpp"

class LcarsFrame : public QWidget {

Q_OBJECT

public:
  LcarsFrame();
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

  virtual QSize sizeHint() const;

protected:
  void paintEvent(QPaintEvent* event);

private:
  QWidget* mContent;
  static const int OFFSET = 5;
  static const int DIAMETER = 30;

};

#endif // LCARS_FRAME
