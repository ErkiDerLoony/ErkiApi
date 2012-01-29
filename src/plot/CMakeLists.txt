add_subdirectory(drawers)
#add_subdirectory(style)
#add_subdirectory(axes)

set(SRC
  plot2d.cpp
)

include_directories(${CMAKE_CURRENT_BINARY_DIR})
include_directories(${PROJECT_SOURCE_DIR}/src/plot)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/style)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/style/wrappers)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/drawers)
include_directories(${PROJECT_SOURCE_DIR}/src/plot/axes)

add_library(plot SHARED ${SRC} ${MOC_SRC})
target_link_libraries(plot ${QT_LIBRARIES})
target_link_libraries(plot drawers)
#target_link_libraries(plot axes)
#target_link_libraries(plot style)

add_library(plot_static STATIC ${SRC} ${MOC_SRC})
target_link_libraries(plot_static ${QT_LIBRARIES})
target_link_libraries(plot_static drawers)
#target_link_libraries(plot_static axes)
#target_link_libraries(plot_static style)

install(TARGETS plot DESTINATION ${CMAKE_BINARY_DIR})
