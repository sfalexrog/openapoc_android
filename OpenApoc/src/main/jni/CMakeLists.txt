# Main multi-project

cmake_minimum_required(VERSION 3.6)
project(OPENAPOC_ANDROID)


# We have to build OpenApoc on the host machine to generate all necessary
# resources (code generation, etc). In fact, we don't even have to build
# everything, only the generators should suffice

# Build with host cmake, error out if not found
if(ANDROID)
    # TODO: Parallelize build while staying cross-platform??
    find_program(CMAKE_HOST cmake)
    if (NOT CMAKE_HOST)
        message(WARNING "Could not find host CMake, using one bundled with Android")
        set(CMAKE_HOST ${CMAKE_COMMAND})
    endif()
    message(STATUS "Host CMake executable is located at ${CMAKE_HOST}")
    message(STATUS "Building in ${CMAKE_BINARY_DIR}")
    # Android creates its CMake builds in .externalNativeBuild/cmake/<debug|release>/<abi>
    # We'll piggyback on this, placing our host build at .externalNativeBuild/host
    # This will point to .externalNativeBuild/cmake/<debug|release>
    get_filename_component(HOST_BUILD_DIR ${CMAKE_BINARY_DIR} DIRECTORY)
    # This will point to .externalNativeBuild/cmake
    get_filename_component(HOST_BUILD_DIR ${HOST_BUILD_DIR} DIRECTORY)
    # This will point to .externalNativeBuild
    get_filename_component(HOST_BUILD_DIR ${HOST_BUILD_DIR} DIRECTORY)
    set(HOST_BUILD_DIR ${HOST_BUILD_DIR}/host)
    message(STATUS "Building for host in ${HOST_BUILD_DIR}")
    if (NOT EXISTS ${HOST_BUILD_DIR})
        file(MAKE_DIRECTORY ${HOST_BUILD_DIR})
    endif()
    execute_process(COMMAND ${CMAKE_HOST} ${PROJECT_SOURCE_DIR}/OpenApoc
        WORKING_DIRECTORY ${HOST_BUILD_DIR}
        )
    #execute_process(COMMAND ${CMAKE_HOST} --build ${HOST_BUILD_DIR} --target OpenApoc -- -j4
    #    WORKING_DIRECTORY ${HOST_BUILD_DIR}
    #    )
    execute_process(COMMAND ${CMAKE_HOST} --build ${HOST_BUILD_DIR} --target extract-data
        WORKING_DIRECTORY ${HOST_BUILD_DIR}
    )
endif()

add_subdirectory(SDL)
add_subdirectory(icu55)
add_subdirectory(boost_slim)

# OpenApoc expects everything to start at root source. Should probably change that, but
# for now work around that.
include_directories(OpenApoc)
add_subdirectory(OpenApoc)
