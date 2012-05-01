find_path(liberkiutil_INCLUDES
  NAMES log.hpp
  PATHS erkiapi/util/
)

find_library(liberkiutil_LIBRARIES
  NAMES liberkiutil.so
)

if(liberkiutil_LIBRARIES)
  set(liberkiutil_FOUND TRUE)
else()
  if(liberkiutil_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find liberkiutil!")
  endif()
endif()
