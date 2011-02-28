set(SRC
  NullPointerException.cpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
#qt4_automoc(${SRC})

include_directories(${CMAKE_CURRENT_BINARY_DIR})

add_library(util STATIC ${SRC} ${MOC_SRC})
target_link_libraries(util ${QT_LIBRARIES})
