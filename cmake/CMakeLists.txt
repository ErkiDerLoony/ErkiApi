file(GLOB FILES ${CMAKE_CURRENT_SOURCE_DIR}/*.cmake)

install(FILES ${FILES} DESTINATION share/cmake-2.8/Modules)
