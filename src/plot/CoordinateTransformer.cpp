#include "CoordinateTransformer.hpp"

#include <Qt/qpoint.h>

CoordinateTransformer::CoordinateTransformer(Range<double> xRange,
                                             Range<double> yRange,
                                             Range<int> xPixelRange,
                                             Range<int> yPixelRange) :
  mXRange(xRange),
  mYRange(yRange),
  mXPixelRange(xPixelRange),
  mYPixelRange(yPixelRange)
{
  // Nothing else to do here.
}

void CoordinateTransformer::screen(const QPointF& src, QPoint& dst) {
  const int x = (int) ((mXPixelRange.from() - 1) + ((src.x() - mXRange.from()) / (mXRange.to() - mXRange.from())) * (mXPixelRange.length() + 1));
  const int y = (int) ((mYPixelRange.from() - 1) + ((src.y() - mYRange.to()) / (mYRange.from() - mYRange.to())) * (mYPixelRange.length() + 1));
  dst.setX(x);
  dst.setY(y);
}

void CoordinateTransformer::math(const QPoint& src, QPointF& dst) {
  const double x = mXRange.from() + (((src.x() - mXPixelRange.from()) / (double) mXPixelRange.length()) * (mXRange.to() - mXRange.from()));
  const double y = mYRange.to() - (((src.y() - mYPixelRange.from()) / (double) mYPixelRange.length()) * (mYRange.to() - mYRange.from()));
  dst.setX(x);
  dst.setY(y);
}

const Range<double>& CoordinateTransformer::xRange() {
  return mXRange;
}

void CoordinateTransformer::setXRange(const Range<double>& range) {
  mXRange = range;
}

const Range<double>& CoordinateTransformer::yRange() {
  return mYRange;
}

void CoordinateTransformer::setYRange(const Range<double>& range) {
  mYRange = range;
}

const Range<int>& CoordinateTransformer::xPixelRange() {
  return mXPixelRange;
}

void CoordinateTransformer::setXPixelRange(const Range<int>& range) {
  mXPixelRange = range;
}

const Range<int>& CoordinateTransformer::yPixelRange() {
  return mYPixelRange;
}

void CoordinateTransformer::setYPixelRange(const Range<int>& range) {
  mYPixelRange = range;
}
