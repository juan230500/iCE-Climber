cmake_minimum_required(VERSION 3.15)
project(Cliente C)

set(CMAKE_C_STANDARD 11)

add_executable(Cliente main.c Sockets/cJSON.c)
add_executable(TEST_PLAYER Test/test_Player.c Sockets/cJSON.c)
add_executable(TEST_OBSERVER Test/test_Observer.c Sockets/cJSON.c)