find_path(liberkiapi_INCLUDES
  NAMES Plot2d.hpp
  PATHS erkiapi/plot/
)

find_library(liberkiapi_LIBRARIES
  NAMES liberkiutil.so
  NAMES liberkiplot.so
)

if(liberkiapi_LIBRARIES)
  set(liberkiapi_FOUND TRUE)
else()
  if(liberkiapi_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find liberkiapi!")
  endif()
endif()
