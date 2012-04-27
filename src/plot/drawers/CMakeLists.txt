file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp)
file(GLOB HEADER ${CMAKE_CURRENT_SOURCE_DIR}/*.hpp)

add_library(drawers STATIC ${SRC})

install(FILES ${HEADER} DESTINATION include/erkiapi/plot)
