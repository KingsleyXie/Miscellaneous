# Wrote By Kingsley, 2018.12
# Develop Environment: gcc version 7.3.0 (Ubuntu 7.3.0-27ubuntu1~18.04)
# For the compiler coursework: SAMPLE-Compiler



cmake_minimum_required (VERSION 3.1)
project (SAMPLE-Compiler CXX)

# For C++11 standard
set(CMAKE_CXX_STANDARD 11)

# For gdb debug mode
set(CMAKE_BUILD_TYPE "Debug")
set(CMAKE_CXX_FLAGS_DEBUG "$ENV{CXXFLAGS} -O0 -Wall -g -ggdb")
set(CMAKE_CXX_FLAGS_RELEASE "$ENV{CXXFLAGS} -O3 -Wall")

add_executable(main main.cpp)
