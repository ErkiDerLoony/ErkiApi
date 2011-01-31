add_subdirectory(axes)

include_directories(${PROJECT_SOURCE_DIR}/src/plot)

add_library(drawers ${SRC})
target_link_libraries(drawers axes)
