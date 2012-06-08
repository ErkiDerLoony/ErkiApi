#include "CoordinateTransformer.hpp"

#include <Qt/qpoint.h>

CoordinateTransformer::CoordinateTransformer(Range<double> xRange,
                                             Range<double> yRange,
                                             Range<int> xPixelRange,
                                             Range<int> yPixelRange) :
  m_xRange(xRange),
  m_yRange(yRange),
  m_xPixelRange(xPixelRange),
  m_yPixelRange(yPixelRange)
{
  // Nothing else to do here.
}

void CoordinateTransformer::screen(const QPointF& src, QPoint& dst) {
  const int x = (int) ((m_xPixelRange.from() - 1) + ((src.x() - m_xRange.from()) / (m_xRange.to() - m_xRange.from())) * (m_xPixelRange.length() + 1));
  const int y = (int) ((m_yPixelRange.from() - 1) + ((src.y() - m_yRange.to()) / (m_yRange.from() - m_yRange.to())) * (m_yPixelRange.length() + 1));
  dst.setX(x);
  dst.setY(y);
}

void CoordinateTransformer::math(const QPoint& src, QPointF& dst) {
  const double x = m_xRange.from() + (((src.x() - m_xPixelRange.from()) / (double) m_xPixelRange.length()) * (m_xRange.to() - m_xRange.from()));
  const double y = m_yRange.to() - (((src.y() - m_yPixelRange.from()) / (double) m_yPixelRange.length()) * (m_yRange.to() - m_yRange.from()));
  dst.setX(x);
  dst.setY(y);
}

const Range<double>& CoordinateTransformer::xRange() {
  return m_xRange;
}

void CoordinateTransformer::setXRange(const Range<double>& range) {
  m_xRange = range;
}

const Range<double>& CoordinateTransformer::yRange() {
  return m_yRange;
}

void CoordinateTransformer::setYRange(const Range<double>& range) {
  m_yRange = range;
}

const Range<int>& CoordinateTransformer::xPixelRange() {
  return m_xPixelRange;
}

void CoordinateTransformer::setXPixelRange(const Range<int>& range) {
  m_xPixelRange = range;
}

const Range<int>& CoordinateTransformer::yPixelRange() {
  return m_yPixelRange;
}

void CoordinateTransformer::setYPixelRange(const Range<int>& range) {
  m_yPixelRange = range;
}
