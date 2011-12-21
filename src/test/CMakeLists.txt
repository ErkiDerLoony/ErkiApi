set(CLI_SRC
  command_line_parser_test.cpp
)

set(PLOT_SRC
  PlotTest.cpp
)

set(LOG_SRC
  log_test.cpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
qt4_automoc(${PLOT_SRC})

include_directories(${PROJECT_SOURCE_DIR}/src/plot)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/style)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/drawers)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/axes)
include_directories(${PROJECT_SOURCE_DIR}/src/lcars)
include_directories(${PROJECT_SOURCE_DIR}/src/util)

add_executable(testplot ${PLOT_SRC} ${MOC_SRC})
target_link_libraries(testplot ${QT_LIBRARIES})
target_link_libraries(testplot plot_static)
target_link_libraries(testplot lcars_static)
target_link_libraries(testplot util_static)
install(TARGETS testplot DESTINATION ${CMAKE_BINARY_DIR})

add_executable(testlog ${LOG_SRC})
target_link_libraries(testlog util_static)
install(TARGETS testlog DESTINATION ${CMAKE_BINARY_DIR})

add_executable(testcommandlineparser ${CLI_SRC})
target_link_libraries(testcommandlineparser util_static)
install(TARGETS testcommandlineparser DESTINATION ${CMAKE_BINARY_DIR})
