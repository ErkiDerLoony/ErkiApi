add_subdirectory(axes)

set(SRC
  Point.cpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})

include_directories(${PROJECT_SOURCE_DIR}/src/plot)

add_library(drawers ${SRC})
target_link_libraries(drawers axes)
