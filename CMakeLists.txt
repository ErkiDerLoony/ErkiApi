project(ErkiApi CXX C)
cmake_minimum_required(VERSION 2.6)
add_definitions("-Wall -O2 -g")

add_subdirectory(src)

find_package(Doxygen)

if (DOXYGEN_FOUND)
  add_custom_target(doc ${DOXYGEN_EXECUTABLE}
                    ${CMAKE_CURRENT_SOURCE_DIR}/Doxyfile
                    WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
                    VERBATIM)
  set_property(DIRECTORY APPEND PROPERTY
               ADDITIONAL_MAKE_CLEAN_FILES doc)
endif (DOXYGEN_FOUND)
