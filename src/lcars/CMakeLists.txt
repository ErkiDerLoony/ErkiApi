set(SRC
  LcarsFrame.cpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
qt4_automoc(${SRC})

include_directories(${CMAKE_CURRENT_BINARY_DIR})
include_directories(${PROJECT_SOURCE_DIR}/src/util)

add_library(lcars STATIC ${SRC} ${MOC_SRC})
target_link_libraries(lcars ${QT_LIBRARIES})
target_link_libraries(lcars util)