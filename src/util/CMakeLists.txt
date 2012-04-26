file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp)

file(GLOB HEADER ${CMAKE_CURRENT_SOURCE_DIR}/*.hpp)

add_library(erkiutil SHARED ${SRC})

install(TARGETS erkiutil LIBRARY DESTINATION lib)
install(FILES ${HEADER} DESTINATION include)
