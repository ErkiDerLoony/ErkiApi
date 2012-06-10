#ifndef RANGE_HPP
#define RANGE_HPP

/**
 * This template class implements ranges of various types.
 *
 * @author Edgar Kalkowski <eMail@edgar-kalkowski.de>
 */
template<class T> class Range {

public:

  Range(const T from, const T to);

  virtual ~Range();

  T from() const;
  T to() const;
  T length() const;

  void setFrom(const T from);
  void setTo(const T to);

  const bool operator==(const Range<T> other) const;
  const bool operator!=(const Range<T> other) const;

private:

  T mFrom;
  T mTo;

};

template<class T> Range<T>::Range(const T from, const T to) :
  mFrom(from), mTo(to) {
}

template<class T> Range<T>::~Range() {
}

template<class T> const bool Range<T>::operator==(const Range<T> other) const {

  if (from() == other.from() && to() == other.to()) {
    return true;
  } else {
    return false;
  }
}

template<class T> const bool Range<T>::operator!=(const Range<T> other) const {

  if (from() != other.from() || to() != other.to()) {
    return true;
  } else {
    return false;
  }
}

template<class T> T Range<T>::from() const {
  return mFrom;
}

template<class T> void Range<T>::setFrom(const T from) {
  mFrom = from;
}

template<class T> T Range<T>::to() const {
  return mTo;
}

template<class T> void Range<T>::setTo(const T to) {
  mTo = to;
}

template<class T> T Range<T>::length() const {
  return mTo - mFrom;
}

#endif /* RANGE_HPP */
