set(SRC
  StyleProvider.cpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})

#include_directories(${PROJECT_SOURCE_DIR}/src/plot)
#include_directories(${PROJECT_SOURCE_DIR}/src/plot/drawers)

add_library(style ${SRC})
