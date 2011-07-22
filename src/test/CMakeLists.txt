set(PLOT_SRC
  PlotTest.cpp
)

set(LOG_SRC
  LogTest.cpp
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
target_link_libraries(testplot plot)
target_link_libraries(testplot lcars)
target_link_libraries(testplot util)
install(TARGETS testplot DESTINATION ${CMAKE_BINARY_DIR})

add_executable(testlog ${LOG_SRC})
target_link_libraries(testlog util)
install(TARGETS testlog DESTINATION ${CMAKE_BINARY_DIR})
