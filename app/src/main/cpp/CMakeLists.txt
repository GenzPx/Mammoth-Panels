cmake_minimum_required(VERSION 3.22.1)
project(mammoth_native)

add_library(
        mammoth_native
        SHARED
        native_helper.c
)

target_link_libraries(mammoth_native android log)
