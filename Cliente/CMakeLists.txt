cmake_minimum_required(VERSION 3.15)
project(Cliente C)

set(CMAKE_C_STANDARD 11)

add_executable(Cliente main.c Sockets/cJSON.c)
add_executable(TEST_SEND Test/test_Send.c)
add_executable(TEST_LISTEN Test/test_Listen.c)
add_executable(TEST_SOCKETS Test/Test_sockets.c)
add_executable(TEST_JSON Test/test_Json.c Sockets/cJSON.c)
add_executable(TEST_PLAYER Test/test_Player.c Sockets/cJSON.c)
add_executable(TEST_OBSERVER Test/test_Observer.c Sockets/cJSON.c)