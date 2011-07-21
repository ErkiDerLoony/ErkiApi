set(SRC
  StyleProvider.cpp
  wrappers/Colour.hpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})

include_directories(${PROJECT_SOURCE_DIR}/src/plot/style)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/style/wrappers)

add_library(style ${SRC})
