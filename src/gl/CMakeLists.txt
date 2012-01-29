set(SRC
  frame.cpp
)

find_package(OpenGL REQUIRED)
find_package(GLUT REQUIRED)

add_library(erkigl ${SRC})
target_link_libraries(erkigl erkiutil)
target_link_libraries(erkigl ${OPENGL_LIBRARIES})
target_link_libraries(erkigl ${GLUT_LIBRARIES})

install(TARGETS erkigl DESTINATION ${CMAKE_BINARY_DIR})
