add_subdirectory(drawers)

set(SRC
  Plot2d.cpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
qt4_automoc(${SRC})

include_directories(${CMAKE_CURRENT_BINARY_DIR})
include_directories(${PROJECT_SOURCE_DIR}/src/plot)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/drawers)

add_library(erkiplot SHARED ${SRC} ${MOC_SRC})
target_link_libraries(erkiplot ${QT_LIBRARIES})
target_link_libraries(erkiplot drawers)
