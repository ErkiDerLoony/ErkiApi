find_path(liberkiplot_INCLUDES
  NAMES Plot2d.hpp
  PATHS erkiapi/plot/
)

find_library(liberkiplot_LIBRARIES
  NAMES liberkiplot.so
)

if(liberkiplot_LIBRARIES)
  set(liberkiplot_FOUND TRUE)
else()
  if(liberkiplot_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find liberkiplot!")
  endif()
endif()
