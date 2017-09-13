# Slimmed-down Boost version for OpenApoc cross-compilation

# We had 2 XML parsers, 75 OpenGL extensions, 5 target platforms,
# a whole galaxy of code and data generators... Also, a CMake file
# in every folder, a cotire module, a separate Visual Studio project,
# a Boost dependency, and half dozen submodules. Not that we needed
# all that for the build, but once you get locked into a serious
# library collection, the tendency is to push it as far as you can.
# The only thing that really worried me was Boost. There is nothing
# in the world more helpless and irresponsible and depraved than an
# IDE in the depths of Boost indexing, and I knoew we'd get into
# that rotten stuff pretty soon.

cmake_minimum_required(VERSION 3.6)
project(Boost_android)

set(Boost_atomic_PATH ${PROJECT_SOURCE_DIR}/libs/atomic)
set(Boost_chrono_PATH ${PROJECT_SOURCE_DIR}/libs/chrono)
set(Boost_date_time_PATH ${PROJECT_SOURCE_DIR}/libs/date_time)
set(Boost_exception_PATH ${PROJECT_SOURCE_DIR}/libs/exception)
set(Boost_filesystem_PATH ${PROJECT_SOURCE_DIR}/libs/filesystem)
set(Boost_locale_PATH ${PROJECT_SOURCE_DIR}/libs/locale)
set(Boost_program_options_PATH ${PROJECT_SOURCE_DIR}/libs/program_options)
set(Boost_regex_PATH ${PROJECT_SOURCE_DIR}/libs/regex)
set(Boost_system_PATH ${PROJECT_SOURCE_DIR}/libs/system)
set(Boost_test_PATH ${PROJECT_SOURCE_DIR}/libs/test)
set(Boost_thread_PATH ${PROJECT_SOURCE_DIR}/libs/thread)
set(Boost_timer_PATH ${PROJECT_SOURCE_DIR}/libs/timer)

set(Boost_INCLUDE_PATH ${PROJECT_SOURCE_DIR})

set(Boost_atomic_FILES
    ${Boost_atomic_PATH}/src/lockpool.cpp
    )

set(Boost_chrono_FILES
    ${Boost_chrono_PATH}/src/chrono.cpp
    ${Boost_chrono_PATH}/src/process_cpu_clocks.cpp
    ${Boost_chrono_PATH}/src/thread_clock.cpp
    )

set(Boost_date_time_FILES
    ${Boost_date_time_PATH}/src/gregorian/date_generators.cpp
    ${Boost_date_time_PATH}/src/gregorian/greg_month.cpp
    ${Boost_date_time_PATH}/src/gregorian/gregorian_types.cpp
    ${Boost_date_time_PATH}/src/gregorian/greg_weekday.cpp
    ${Boost_date_time_PATH}/src/posix_time/posix_time_types.cpp
    )

set(Boost_exception_FILES
    ${Boost_exception_PATH}/src/clone_current_exception_non_intrusive.cpp
    )

set(Boost_filesystem_FILES
    ${Boost_filesystem_PATH}/src/codecvt_error_category.cpp
    ${Boost_filesystem_PATH}/src/operations.cpp
    ${Boost_filesystem_PATH}/src/path.cpp
    ${Boost_filesystem_PATH}/src/path_traits.cpp
    ${Boost_filesystem_PATH}/src/portability.cpp
    ${Boost_filesystem_PATH}/src/unique_path.cpp
    ${Boost_filesystem_PATH}/src/utf8_codecvt_facet.cpp
    ${Boost_filesystem_PATH}/src/windows_file_codecvt.cpp
    )

set(Boost_locale_FILES
    ${Boost_locale_PATH}/src/encoding/codepage.cpp
    ${Boost_locale_PATH}/src/icu/boundary.cpp
    ${Boost_locale_PATH}/src/icu/codecvt.cpp
    ${Boost_locale_PATH}/src/icu/collator.cpp
    ${Boost_locale_PATH}/src/icu/conversion.cpp
    ${Boost_locale_PATH}/src/icu/date_time.cpp
    ${Boost_locale_PATH}/src/icu/formatter.cpp
    ${Boost_locale_PATH}/src/icu/icu_backend.cpp
    ${Boost_locale_PATH}/src/icu/numeric.cpp
    ${Boost_locale_PATH}/src/icu/time_zone.cpp
    ${Boost_locale_PATH}/src/shared/date_time.cpp
    ${Boost_locale_PATH}/src/shared/format.cpp
    ${Boost_locale_PATH}/src/shared/formatting.cpp
    ${Boost_locale_PATH}/src/shared/generator.cpp
    ${Boost_locale_PATH}/src/shared/ids.cpp
    ${Boost_locale_PATH}/src/shared/localization_backend.cpp
    ${Boost_locale_PATH}/src/shared/message.cpp
    ${Boost_locale_PATH}/src/shared/mo_lambda.cpp
    ${Boost_locale_PATH}/src/std/codecvt.cpp
    ${Boost_locale_PATH}/src/std/collate.cpp
    ${Boost_locale_PATH}/src/std/converter.cpp
    ${Boost_locale_PATH}/src/std/numeric.cpp
    ${Boost_locale_PATH}/src/std/std_backend.cpp
    ${Boost_locale_PATH}/src/util/codecvt_converter.cpp
    ${Boost_locale_PATH}/src/util/default_locale.cpp
    ${Boost_locale_PATH}/src/util/gregorian.cpp
    ${Boost_locale_PATH}/src/util/info.cpp
    ${Boost_locale_PATH}/src/util/locale_data.cpp
    )

set(Boost_program_options_FILES
    ${Boost_program_options_PATH}/src/cmdline.cpp
    ${Boost_program_options_PATH}/src/config_file.cpp
    ${Boost_program_options_PATH}/src/convert.cpp
    ${Boost_program_options_PATH}/src/options_description.cpp
    ${Boost_program_options_PATH}/src/parsers.cpp
    ${Boost_program_options_PATH}/src/positional_options.cpp
    ${Boost_program_options_PATH}/src/split.cpp
    ${Boost_program_options_PATH}/src/utf8_codecvt_facet.cpp
    ${Boost_program_options_PATH}/src/value_semantic.cpp
    ${Boost_program_options_PATH}/src/variables_map.cpp
    ${Boost_program_options_PATH}/src/winmain.cpp
    )

set(Boost_regex_FILES
    ${Boost_regex_PATH}/src/cpp_regex_traits.cpp
    ${Boost_regex_PATH}/src/cregex.cpp
    ${Boost_regex_PATH}/src/c_regex_traits.cpp
    ${Boost_regex_PATH}/src/fileiter.cpp
    ${Boost_regex_PATH}/src/icu.cpp
    ${Boost_regex_PATH}/src/instances.cpp
    ${Boost_regex_PATH}/src/posix_api.cpp
    ${Boost_regex_PATH}/src/regex.cpp
    ${Boost_regex_PATH}/src/regex_debug.cpp
    ${Boost_regex_PATH}/src/regex_raw_buffer.cpp
    ${Boost_regex_PATH}/src/regex_traits_defaults.cpp
    ${Boost_regex_PATH}/src/static_mutex.cpp
    ${Boost_regex_PATH}/src/usinstances.cpp
    ${Boost_regex_PATH}/src/w32_regex_traits.cpp
    ${Boost_regex_PATH}/src/wc_regex_traits.cpp
    ${Boost_regex_PATH}/src/wide_posix_api.cpp
    ${Boost_regex_PATH}/src/winstances.cpp
    )

set(Boost_system_FILES
    ${Boost_system_PATH}/src/error_code.cpp
    )

set(Boost_test_FILES
    ${Boost_test_PATH}/src/compiler_log_formatter.cpp
    ${Boost_test_PATH}/src/cpp_main.cpp
    ${Boost_test_PATH}/src/debug.cpp
    ${Boost_test_PATH}/src/decorator.cpp
    ${Boost_test_PATH}/src/execution_monitor.cpp
    ${Boost_test_PATH}/src/framework.cpp
    ${Boost_test_PATH}/src/junit_log_formatter.cpp
    ${Boost_test_PATH}/src/plain_report_formatter.cpp
    ${Boost_test_PATH}/src/progress_monitor.cpp
    ${Boost_test_PATH}/src/results_collector.cpp
    ${Boost_test_PATH}/src/results_reporter.cpp
    ${Boost_test_PATH}/src/test_framework_init_observer.cpp
    ${Boost_test_PATH}/src/test_main.cpp
    ${Boost_test_PATH}/src/test_tools.cpp
    ${Boost_test_PATH}/src/test_tree.cpp
    ${Boost_test_PATH}/src/unit_test_log.cpp
    ${Boost_test_PATH}/src/unit_test_main.cpp
    ${Boost_test_PATH}/src/unit_test_monitor.cpp
    ${Boost_test_PATH}/src/unit_test_parameters.cpp
    ${Boost_test_PATH}/src/xml_log_formatter.cpp
    ${Boost_test_PATH}/src/xml_report_formatter.cpp
    )

set(Boost_thread_FILES
    ${Boost_thread_PATH}/src/future.cpp
    ${Boost_thread_PATH}/src/pthread/once_atomic.cpp
    ${Boost_thread_PATH}/src/pthread/once.cpp
    ${Boost_thread_PATH}/src/pthread/thread.cpp
    ${Boost_thread_PATH}/src/tss_null.cpp
    )

set(Boost_timer_FILES
    ${Boost_timer_PATH}/src/auto_timers_construction.cpp
    ${Boost_timer_PATH}/src/cpu_timer.cpp
    )

add_library(Boost_atomic ${Boost_atomic_FILES})
target_include_directories(Boost_atomic SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_chrono           ${Boost_chrono_FILES})
target_include_directories(Boost_chrono SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_date_time        ${Boost_date_time_FILES})
target_include_directories(Boost_date_time SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_exception        ${Boost_exception_FILES})
target_include_directories(Boost_exception SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_filesystem       ${Boost_filesystem_FILES})
target_include_directories(Boost_filesystem SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_locale           ${Boost_locale_FILES})
target_include_directories(Boost_locale SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)
target_compile_definitions(Boost_locale PRIVATE
    "BOOST_LOCALE_WITH_ICU=1"
    "BOOST_LOCALE_NO_POSIX_BACKEND"
    "BOOST_LOCALE_NO_STD_BACKEND"
    "BOOST_LOCALE_NO_WINAPI_BACKEND"
    )

add_library(Boost_program_options  ${Boost_program_options_FILES})
target_include_directories(Boost_program_options SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_regex            ${Boost_regex_FILES})
target_include_directories(Boost_regex SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_system           ${Boost_system_FILES})
target_include_directories(Boost_system SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_test             ${Boost_test_FILES})
target_include_directories(Boost_test SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_thread           ${Boost_thread_FILES})
target_include_directories(Boost_thread SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

add_library(Boost_timer            ${Boost_timer_FILES})
target_include_directories(Boost_timer SYSTEM PUBLIC $<BUILD_INTERFACE:${Boost_INCLUDE_PATH}> $<INSTALL_INTERFACE:.>)

#message("CMAKE_BINARY_DIR is ${CMAKE_BINARY_DIR}")
#set(icu55_DIR ${CMAKE_BINARY_DIR}/icu55)
#message("icu55_DIR set to ${icu55_DIR}")
#find_package(icu55)

target_link_libraries(Boost_chrono PUBLIC Boost_system)
target_link_libraries(Boost_filesystem PUBLIC Boost_system)
target_link_libraries(Boost_locale PRIVATE Boost_thread icuuc_droid icui18n_droid)
target_link_libraries(Boost_test PUBLIC Boost_timer Boost_exception)
target_link_libraries(Boost_thread PUBLIC Boost_atomic Boost_chrono Boost_exception Boost_system)
target_link_libraries(Boost_timer PRIVATE Boost_chrono PUBLIC Boost_system)

set(Boost_TARGETS
    Boost_atomic
    Boost_chrono
    Boost_date_time
    Boost_exception
    Boost_filesystem
    Boost_locale
    Boost_program_options
    Boost_regex
    Boost_system
    Boost_test
    Boost_thread
    Boost_timer
    )

#install(TARGETS ${Boost_TARGETS}
#    EXPORT Boost_android
#    ARCHIVE DESTINATION ${CMAKE_BINARY_DIR}
#    LIBRARY DESTINATION ${CMAKE_BINARY_DIR}
#    )

#install(EXPORT Boost_android
#    DESTINATION lib/Boost
#    )

#export(TARGETS
#    ${Boost_TARGETS}
#    FILE
#    Boost_androidConfig.cmake
#)
