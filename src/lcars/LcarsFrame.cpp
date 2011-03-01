#include <QPainter>
#include <QRectF>
#include <QPen>
#include <QBrush>
#include <QFont>

#include "LcarsFrame.hpp"
#include "LcarsFrame.moc"
#include "Lcars.hpp"

LcarsFrame::LcarsFrame(QString& title) : mContent(new QWidget()) {

  // Make the background black and move the content to the correct position.
  QPalette p;
  p.setColor(QPalette::Background, Qt::black);
  setPalette(p);
  mContent->setPalette(p);
  mContent->resize(100, 20);
  mContent->move(2*OFFSET + DIAMETER, 2*OFFSET + DIAMETER);

  // Adjust minimum size.
  QFontMetrics fm(QFont("Monospace", 14));
  float w = 2.0*OFFSET + BAR_WIDTH + DIAMETER + mContent->sizeHint().width() +
    fm.width(title);
  float h = 2.0*OFFSET + 2.0*DIAMETER + mContent->sizeHint().height();
  setMinimumSize(QSizeF(w, h).toSize());

  setWindowTitle(title);
}

LcarsFrame::~LcarsFrame() {
  delete mContent;
}

QWidget* LcarsFrame::content() {
  return mContent;
}

void LcarsFrame::setContent(QWidget* content) throw(NullPointerException) {

  if (content != NULL) {
    delete mContent;
    mContent = content;
  } else {
    QString s("bla");
    throw NullPointerException(s);
  }
}

void LcarsFrame::paintEvent(QPaintEvent* event) {

  // Setup the painter.
  float radius = 0.5*DIAMETER;
  QPainter p(this);
  p.setPen(Lcars::blue);
  p.setBrush(Lcars::blue);
  p.setRenderHint(QPainter::Antialiasing, true);
  p.setRenderHint(QPainter::TextAntialiasing, true);

  // Draw the top bar.
  p.drawEllipse(QRectF(OFFSET, OFFSET, DIAMETER, DIAMETER));
  p.drawRect(QRectF(OFFSET + radius, OFFSET,
                    width() - OFFSET - OFFSET - DIAMETER, DIAMETER));
  p.drawEllipse(QRectF(width() - OFFSET - DIAMETER, OFFSET,
                       DIAMETER, DIAMETER));

  // Draw the inner corner at the top bar.
  p.drawEllipse(QRectF(OFFSET + BAR_WIDTH - radius,
                       OFFSET + radius, DIAMETER, DIAMETER));
  p.setPen(Qt::black);
  p.setBrush(Qt::black);
  p.drawEllipse(QRectF(OFFSET + BAR_WIDTH, OFFSET + DIAMETER + 1,
                       DIAMETER, DIAMETER));
  p.setPen(Lcars::blue);
  p.setBrush(Lcars::blue);

  // Draw the button bar.
  p.drawRect(QRectF(OFFSET, OFFSET + radius,
                    BAR_WIDTH, height() - OFFSET - OFFSET - DIAMETER));

  // Draw the bottom of the button bar.
  p.drawEllipse(QRectF(OFFSET, height() - OFFSET - DIAMETER,
                       DIAMETER, DIAMETER));
  p.drawRect(QRectF(OFFSET + radius, height() - OFFSET - radius,
                    BAR_WIDTH - DIAMETER, radius));
  p.drawEllipse(QRectF(OFFSET + BAR_WIDTH - DIAMETER,
                       height() - OFFSET - DIAMETER,
                       DIAMETER, DIAMETER));

  // Draw the title
  p.setPen(Qt::black);
  p.setFont(QFont("Monospace", 14));
  QFontMetrics fm = p.fontMetrics();
  p.drawText(OFFSET + BAR_WIDTH + radius,
             OFFSET + radius + 0.5*fm.height() - fm.descent(),
             windowTitle());
}
