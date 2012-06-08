find_path(liberkiapi_INCLUDES
  NAMES Plot2D.hpp
  PATHS erkiapi/plot/
)

find_library(liberkiapi_LIBRARIES
  NAMES liberkiutil.so
  NAMES liberkiplot.so
)

if(liberkiapi_INCLUDES)
  message("Found ErkiApi includes.")
else()
  if(liberkiapi_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find ErkiApi includes!")
  else()
    message("Could not find ErkiApi includes!")
  endif()
endif()

if(liberkiapi_LIBRARIES)
  message("Found ErkiApi libraries.")
  set(liberkiapi_FOUND TRUE)
else()
  if(liberkiapi_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find ErkiApi libraries!")
  else()
    message("Could not find ErkiApi libraries.")
  endif()
endif()
