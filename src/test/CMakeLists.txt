set(SRC
  PlotTest.cpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
qt4_automoc(${SRC})

include_directories(${PROJECT_SOURCE_DIR}/src/plot)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/drawers)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/drawers/axes)

add_executable(test ${SRC} ${MOC_SRC})
target_link_libraries(test ${QT_LIBRARIES})
target_link_libraries(test ${CMAKE_BINARY_DIR}/src/plot/liberkiplot.so)

install(TARGETS test DESTINATION ${CMAKE_BINARY_DIR})
install(FILES ${CMAKE_BINARY_DIR}/src/plot/liberkiplot.so
        DESTINATION ${CMAKE_BINARY_DIR})
