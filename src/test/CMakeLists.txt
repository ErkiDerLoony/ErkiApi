set(CLI_SRC
  test_command_line_parser.cpp
)

set(PLOT_SRC
  test_plot.cpp
)

set(LOG_SRC
  test_log.cpp
)

set(LOGGING_SRC
  test_logging.cpp
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
target_link_libraries(testplot util_static)
target_link_libraries(testplot GL glut)
install(TARGETS testplot DESTINATION ${CMAKE_BINARY_DIR})

add_executable(testlog ${LOG_SRC})
target_link_libraries(testlog util_static)
install(TARGETS testlog DESTINATION ${CMAKE_BINARY_DIR})

add_executable(testcommandlineparser ${CLI_SRC})
target_link_libraries(testcommandlineparser util_static)
install(TARGETS testcommandlineparser DESTINATION ${CMAKE_BINARY_DIR})

add_executable(testlogging ${LOGGING_SRC})
target_link_libraries(testlogging util)
target_link_libraries(testlogging m)
install(TARGETS testlogging DESTINATION ${CMAKE_BINARY_DIR})
