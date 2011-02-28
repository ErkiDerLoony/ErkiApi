#include <QPainter>
#include <QRectF>

#include "LcarsFrame.hpp"
#include "LcarsFrame.moc"

LcarsFrame::LcarsFrame() {
  mContent = new QWidget();
  mContent->setAutoFillBackground(true);
  QPalette p = mContent->palette();
  p.setColor(QPalette::Background, Qt::red);
  mContent->setPalette(p);
}

LcarsFrame::~LcarsFrame() {
  delete mContent;
}

QWidget* LcarsFrame::content() {
  return mContent;
}

void LcarsFrame::setContent(QWidget* content) throw(NullPointerException) {

  if (content != NULL) {
    mContent = content;
  } else {
    QString s("bla");
    throw NullPointerException(s);
  }
}

QSize LcarsFrame::sizeHint() const {
  QSize s = mContent->sizeHint();
  return QSize(s.width() + 2*OFFSET + DIAMETER,
               s.height() + 2*OFFSET + DIAMETER);
}

void LcarsFrame::paintEvent(QPaintEvent* event) {
  QPainter p(this);
  p.setRenderHint(QPainter::Antialiasing, true);
  p.drawArc(QRectF(5.0, 5.0, 10.0, 10.0), 0, 360);
}
