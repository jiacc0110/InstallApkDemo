cmake_minimum_required(VERSION 3.4.1)
add_library(myjni SHARED myjni.c)
find_library(log-lib log)
target_link_libraries(myjni ${log-lib})