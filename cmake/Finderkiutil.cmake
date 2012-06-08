find_path(erkiutil_INCLUDES
  NAMES Plot2D.hpp
)

find_library(erkiutil_LIBRARIES
  NAMES liberkiutil.so
)

if(erkiutil_INCLUDES)
  message(STATUS "Found ErkiUtil includes: ${erkiutil_INCLUDES}")
else()
  if(erkiutil_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find ErkiUtil includes!")
  else()
    message(STATUS "Could not find ErkiUtil includes!")
  endif()
endif()

if(erkiutil_LIBRARIES)
  message(STATUS "Found ErkiUtil libraries: ${erkiutil_LIBRARIES}.")
  set(erkiutil_FOUND TRUE)
else()
  if(erkiutil_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find ErkiUtil libraries!")
  else()
    message(STATUS "Could not find ErkiUtil libraries.")
  endif()
endif()
