set(SRC
  point.cpp
)

include_directories(${PROJECT_SOURCE_DIR}/src/plot)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/drawers)

add_library(drawers STATIC ${SRC})
