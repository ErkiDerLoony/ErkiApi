#ifndef RANGE_HPP
#define RANGE_HPP

template<class T> class Range {

public:

  Range(T from, T to);

  virtual ~Range();

  T from();
  T to();
  T length();

  void setFrom(T from);
  void setTo(T to);

private:

  T mFrom;
  T mTo;

};

template<class T> Range<T>::Range(T from, T to) : mFrom(from), mTo(to) {

}

template<class T> Range<T>::~Range() {

}

template<class T> T Range<T>::from() {
  return mFrom;
}

template<class T> void Range<T>::setFrom(T from) {
  mFrom = from;
}

template<class T> T Range<T>::to() {
  return mTo;
}

template<class T> void Range<T>::setTo(T to) {
  mTo = to;
}

template<class T> T Range<T>::length() {
  return mTo - mFrom;
}

#endif /* RANGE_HPP */
