set(SRC
  null_pointer_exception.cpp
  command_line_parser.cpp
  log.cpp
)

add_library(erkiutil_shared SHARED ${SRC} ${MOC_SRC})
set_target_properties(erkiutil_shared PROPERTIES OUTPUT_NAME erkiutil)

add_library(erkiutil STATIC ${SRC} ${MOC_SRC})

install(TARGETS erkiutil DESTINATION ${CMAKE_BINARY_DIR})
install(TARGETS erkiutil_shared DESTINATION ${CMAKE_BINARY_DIR})
