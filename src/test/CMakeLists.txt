set(CLI_SRC
  test_command_line_parser.cpp
)

set(PLOT_SRC
  test_plot.cpp
)

set(LOG_SRC
  test_log.cpp
)

include_directories(${PROJECT_SOURCE_DIR}/src/plot)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/style)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/drawers)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/axes)
include_directories(${PROJECT_SOURCE_DIR}/src/lcars)
include_directories(${PROJECT_SOURCE_DIR}/src/util)

find_package(OpenGL REQUIRED)

add_executable(testplot ${PLOT_SRC})
target_link_libraries(testplot plot_static)
target_link_libraries(testplot GL glut)
target_link_libraries(testplot erkiutil_static)
install(TARGETS testplot DESTINATION ${CMAKE_BINARY_DIR})

add_executable(testlog ${LOG_SRC})
target_link_libraries(testlog erkiutil_static)
install(TARGETS testlog DESTINATION ${CMAKE_BINARY_DIR})

add_executable(testcommandlineparser ${CLI_SRC})
target_link_libraries(testcommandlineparser erkiutil_static)
install(TARGETS testcommandlineparser DESTINATION ${CMAKE_BINARY_DIR})
