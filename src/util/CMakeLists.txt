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

add_library(erkiutil SHARED ${SRC} ${MOC_SRC})
target_link_libraries(erkiutil ${QT_LIBRARIES})

add_library(erkiutil_static STATIC ${SRC} ${MOC_SRC})
target_link_libraries(erkiutil_static ${QT_LIBRARIES})
set_target_properties(erkiutil_static PROPERTIES OUTPUT_NAME erkiutil)

install(TARGETS erkiutil DESTINATION ${CMAKE_BINARY_DIR})
install(TARGETS erkiutil_static DESTINATION ${CMAKE_BINARY_DIR})
