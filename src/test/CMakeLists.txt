#add_executable(testplot test_plot.cpp)
#target_link_libraries(testplot plot)
#target_link_libraries(testplot erkiutil)
#target_link_libraries(testplot erkigl)
#install(TARGETS testplot DESTINATION bin)

add_executable(testlog test_log.cpp)
target_link_libraries(testlog erkiutil)
install(TARGETS testlog DESTINATION bin)

add_executable(testcommandlineparser test_command_line_parser.cpp)
target_link_libraries(testcommandlineparser erkiutil)
install(TARGETS testcommandlineparser DESTINATION bin)

#add_executable(testgl test_gl.cpp)
#target_link_libraries(testgl erkigl erkiutil)
#install(TARGETS testgl DESTINATION bin)
