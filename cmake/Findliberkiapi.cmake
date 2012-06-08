find_path(liberkiapi_INCLUDES
  NAMES Plot2D.hpp
  PATHS /usr/include/erkiapi/plot
        /usr/local/include/erkiapi/plot
)

find_library(liberkiapi_LIBRARIES
  NAMES liberkiutil.so
  NAMES liberkiplot.so
)

if(liberkiapi_INCLUDES)
  message(STATUS "Found ErkiApi includes at ${liberkiapi_INCLUDES}.")
else()
  if(liberkiapi_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find ErkiApi includes!")
  else()
    message(STATUS "Could not find ErkiApi includes!")
  endif()
endif()

if(liberkiapi_LIBRARIES)
  message(STATUS "Found ErkiApi libraries at ${liberkiapi_LIBRARIES}.")
  set(liberkiapi_FOUND TRUE)
else()
  if(liberkiapi_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find ErkiApi libraries!")
  else()
    message(STATUS "Could not find ErkiApi libraries.")
  endif()
endif()
