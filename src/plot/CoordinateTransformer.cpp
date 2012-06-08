#include "CoordinateTransformer.hpp"

#include <Qt/qpoint.h>

CoordinateTransformer::CoordinateTransformer(const Plot2D& plot)
  : mPlot(plot), mXRange(Range<double>(-1.0, 1.0)), mYRange(Range<double>(-1.0, 1.0)), mXPixelRange(Range<int>(-1, 1)), mYPixelRange(Range<int>(-1, 1)) {}

void CoordinateTransformer::screen(const QPointF& src, QPoint& dst) {
  const int x = (int) ((mXPixelRange.from() - 1) + ((src.x() - mXRange.from()) / (mXRange.to() - mXRange.from())) * (mXPixelRange.length() + 1));
  const int y = (int) ((mYPixelRange.from() - 1) + ((src.y() - mYRange.to()) / (mYRange.from() - mYRange.to())) * (mYPixelRange.length() + 1));
  dst.setX(x);
  dst.setY(y);
}

void CoordinateTransformer::math(const QPoint& src, QPointF& dst) {
  const double x = mXRange.from() + (((src.x() - mXPixelRange.from()) / mXPixelRange.length()) * (mXRange.to() - mXRange.from()));
  const double y = mYRange.to() - (((src.y() - mYPixelRange.from()) / mYPixelRange.length()) * (mYRange.to() - mYRange.from()));
  dst.setX(x);
  dst.setY(y);
}

const Range<double>& CoordinateTransformer::getXRange() {
  return mXRange;
}

void CoordinateTransformer::setXRange(const Range<double>& range) {
  mXRange = range;
}

const Range<double>& CoordinateTransformer::getYRange() {
  return mYRange;
}

void CoordinateTransformer::setYRange(const Range<double>& range) {
  mYRange = range;
}

const Range<int>& CoordinateTransformer::getXPixelRange() {
  return mXPixelRange;
}

void CoordinateTransformer::setXPixelRange(const Range<int>& range) {
  mXPixelRange = range;
}

const Range<int>& CoordinateTransformer::getYPixelRange() {
  return mYPixelRange;
}

void CoordinateTransformer::setYPixelRange(const Range<int>& range) {
  mYPixelRange = range;
}
