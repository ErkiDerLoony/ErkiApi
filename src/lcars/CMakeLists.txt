file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp)
file(GLOB HEADER ${CMAKE_CURRENT_SOURCE_DIR}/*.hpp)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
qt4_automoc(${SRC})

include_directories(${CMAKE_CURRENT_BINARY_DIR})

add_library(lcars SHARED ${SRC} ${MOC_SRC})
target_link_libraries(lcars ${QT_LIBRARIES})
target_link_libraries(lcars erkiutil)

install(TARGETS lcars LIBRARY DESTINATION lib)
install(FILES ${HEADER} DESTINATION include/erkiapi/lcars)
