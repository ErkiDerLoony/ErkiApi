find_path(erkiplot_INCLUDES
  NAMES Plot2D.hpp
  PATHS /usr/include/erkiapi/plot
        /usr/local/include/erkiapi/plot
)

find_library(erkiplot_LIBRARIES
  NAMES liberkiplot.so
)

if(erkiplot_INCLUDES)
  message(STATUS "Found ErkiPlot includes: ${erkiplot_INCLUDES}")
else()
  if(erkiplot_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find ErkiPlot includes!")
  else()
    message(STATUS "Could not find ErkiPlot includes!")
  endif()
endif()

if(erkiplot_LIBRARIES)
  message(STATUS "Found ErkiPlot libraries: ${erkiplot_LIBRARIES}")
  set(erkiplot_FOUND TRUE)
else()
  if(erkiplot_FIND_REQUIRED)
    message(FATAL_ERROR "Could not find ErkiPlot libraries!")
  else()
    message(STATUS "Could not find ErkiPlot libraries.")
  endif()
endif()
