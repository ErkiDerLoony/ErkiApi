file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp)
file(GLOB HEADER ${CMAKE_CURRENT_SOURCE_DIR}/*.hpp)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
qt4_automoc(${SRC})

include_directories(${CMAKE_CURRENT_BINARY_DIR})

add_library(actions STATIC ${SRC} ${MOC_SRC})
