#include "LcarsFrame.hpp"

#include <algorithm>

#include <QPainter>
#include <QRectF>
#include <QPen>
#include <QBrush>
#include <QFont>

#include "Lcars.hpp"
#include "null_pointer_exception.hpp"

const float LcarsFrame::OFFSET = 5.0;
const float LcarsFrame::DIAMETER = 30.0;
const float LcarsFrame::BAR_WIDTH = 130.0;

LcarsFrame::LcarsFrame(const QString& title) : mContent(new QWidget(this)) {

  // Make the background black.
  QPalette p;
  p.setColor(QPalette::Background, Qt::black);
  setPalette(p);

  // Adjust minimum size.
  QFontMetrics fm(QFont("Monospace", 14));
  float w = 2.0*OFFSET + BAR_WIDTH + DIAMETER +
    std::max(mContent->size().width(), fm.width(title));
  float h = 2.0*OFFSET + 2.0*DIAMETER + mContent->size().height();
  setMinimumSize(QSizeF(w, h).toSize());

  setWindowTitle(title);
}

LcarsFrame::~LcarsFrame() {
  delete mContent;
}

QWidget* LcarsFrame::content() {
  return mContent;
}

void LcarsFrame::setContent(QWidget* content) throw(null_pointer_exception) {

  if (content != NULL) {
    delete mContent;
    mContent = content;
  } else {
    throw null_pointer_exception("Content must not be 0!");
  }
}

void LcarsFrame::setLayout(QLayout* layout) {
  mContent->setLayout(layout);
}

void LcarsFrame::resizeEvent(QResizeEvent* event) {
  int w = size().width() - 3*OFFSET - BAR_WIDTH;
  int h = size().height() - 3*OFFSET - DIAMETER;
  mContent->setGeometry(2*OFFSET + BAR_WIDTH, 2*OFFSET + DIAMETER, w, h);
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
  float center = OFFSET + BAR_WIDTH +
    (size().width() - 2.0*OFFSET - radius - BAR_WIDTH) / 2.0;
  p.drawText(center - 0.5*fm.width(windowTitle()),
             OFFSET + radius + 0.5*fm.height() - fm.descent(),
             windowTitle());
}

#include "LcarsFrame.moc"
