set(SRC
  command_line_parser.cpp
  log.cpp
)

add_library(erkiutil SHARED ${SRC})

install(TARGETS erkiutil LIBRARY DESTINATION lib)
