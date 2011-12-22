set(SRC
  null_pointer_exception.cpp
  command_line_parser.cpp
  log.cpp
  logging.cpp
)

find_package(Qt4 REQUIRED)
include(${QT_USE_FILE})
#qt4_automoc(${SRC})

include_directories(${CMAKE_CURRENT_BINARY_DIR})

add_library(util SHARED ${SRC} ${MOC_SRC})
target_link_libraries(util ${QT_LIBRARIES})

install(TARGETS util DESTINATION ${CMAKE_BINARY_DIR})
