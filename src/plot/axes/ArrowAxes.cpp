#include <QPainter>

#include "CoordinateTransformer.hpp"
#include "Drawer.hpp"
#include "ArrowAxes.hpp"

ArrowAxes::~ArrowAxes() {}

double ArrowAxes::getMinX() {
  return minX;
}

void ArrowAxes::setMinX(double minX) {
  this->minX = minX;
}

double ArrowAxes::getMaxX() {
  return maxX;
}

void ArrowAxes::setMaxX(double maxX) {
  this->maxX = maxX;
}

double ArrowAxes::getMinY() {
  return minY;
}

void ArrowAxes::setMinY(double minY) {
  this->minY = minY;
}

double ArrowAxes::getMaxY() {
  return maxY;
}

void ArrowAxes::setMaxY(double maxY) {
  this->maxY = maxY;
}

void ArrowAxes::draw(QPainter& p, CoordinateTransformer& t) {

}
