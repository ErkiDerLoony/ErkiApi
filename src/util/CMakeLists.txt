set(SRC
  command_line_parser.cpp
  log.cpp
)

set(HEADER
  command_line_parser.hpp
  log.hpp
)

add_library(erkiutil SHARED ${SRC})

install(TARGETS erkiutil LIBRARY DESTINATION lib)
install(FILES ${HEADER} DESTINATION include)
