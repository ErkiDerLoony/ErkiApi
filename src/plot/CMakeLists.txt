file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp)
file(GLOB HEADER ${CMAKE_CURRENT_SOURCE_DIR}/*.hpp)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
qt4_automoc(${SRC})

include_directories(${CMAKE_CURRENT_BINARY_DIR})

add_subdirectory(drawers)
#add_subdirectory(style)
#add_subdirectory(axes)

add_library(plot SHARED ${SRC} ${MOC_SRC})
target_link_libraries(plot ${QT_LIBRARIES})
target_link_libraries(plot drawers)
#target_link_libraries(plot axes)
#target_link_libraries(plot style)

install(TARGETS plot LIBRARY DESTINATION lib)
install(FILES ${HEADER} DESTINATION include/erkiapi/plot)
