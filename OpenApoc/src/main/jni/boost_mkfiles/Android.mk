# Some of Boost's libraries used by OpenApoc do require compilation

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := boost

# LOCAL_STATIC_LIBRARIES := libicuuc_static

BOOST_LIBS_DIR = $(LOCAL_PATH)/../boost_libs

LOCAL_C_INCLUDES += $(BOOST_LIBS_DIR)/algorithm/include \
       $(BOOST_LIBS_DIR)/align/include  \
       $(BOOST_LIBS_DIR)/array/include \
       $(BOOST_LIBS_DIR)/assert/include \
       $(BOOST_LIBS_DIR)/atomic/include \
       $(BOOST_LIBS_DIR)/bind/include \
       $(BOOST_LIBS_DIR)/chrono/include \
       $(BOOST_LIBS_DIR)/concept_check/include \
       $(BOOST_LIBS_DIR)/config/include \
       $(BOOST_LIBS_DIR)/container/include \
       $(BOOST_LIBS_DIR)/core/include \
       $(BOOST_LIBS_DIR)/date_time/include \
       $(BOOST_LIBS_DIR)/detail/include \
       $(BOOST_LIBS_DIR)/exception/include \
       $(BOOST_LIBS_DIR)/filesystem/include \
       $(BOOST_LIBS_DIR)/format/include \
       $(BOOST_LIBS_DIR)/function/include \
       $(BOOST_LIBS_DIR)/functional/include \
       $(BOOST_LIBS_DIR)/integer/include \
       $(BOOST_LIBS_DIR)/intrusive/include \
       $(BOOST_LIBS_DIR)/io/include \
       $(BOOST_LIBS_DIR)/iterator/include \
       $(BOOST_LIBS_DIR)/lexical_cast/include \
       $(BOOST_LIBS_DIR)/locale/include \
       $(BOOST_LIBS_DIR)/math/include \
       $(BOOST_LIBS_DIR)/move/include \
       $(BOOST_LIBS_DIR)/mpl/include \
       $(BOOST_LIBS_DIR)/multiprecision/include \
       $(BOOST_LIBS_DIR)/numeric_conversion/include \
       $(BOOST_LIBS_DIR)/optional/include \
       $(BOOST_LIBS_DIR)/predef/include \
       $(BOOST_LIBS_DIR)/preprocessor/include \
       $(BOOST_LIBS_DIR)/range/include \
       $(BOOST_LIBS_DIR)/ratio/include \
       $(BOOST_LIBS_DIR)/rational/include \
       $(BOOST_LIBS_DIR)/smart_ptr/include \
       $(BOOST_LIBS_DIR)/static_assert/include \
       $(BOOST_LIBS_DIR)/system/include \
       $(BOOST_LIBS_DIR)/thread/include \
       $(BOOST_LIBS_DIR)/throw_exception/include \
       $(BOOST_LIBS_DIR)/tokenizer/include \
       $(BOOST_LIBS_DIR)/tuple/include \
       $(BOOST_LIBS_DIR)/type_index/include \
       $(BOOST_LIBS_DIR)/type_traits/include \
       $(BOOST_LIBS_DIR)/unordered/include \
       $(BOOST_LIBS_DIR)/utility/include \
       $(BOOST_LIBS_DIR)/uuid/include

# No luck building icu as a separate library... maybe just build it into the boost lib?

ICU_DIR := $(LOCAL_PATH)/../icu55

LOCAL_C_INCLUDES += $(ICU_DIR)/source/common \
        $(ICU_DIR)/source/common/unicode \
        $(ICU_DIR)/source/i18n

ICU_CFLAGS := -DICU_DATA_DIR_PREFIX_ENV_VAR=\"ANDROID_ROOT\" \
              -DICU_DATA_DIR=\"/usr/icu\" \
              -DU_HAVE_NL_LANGINFO_CODESET=0 \
              -D_REENTRANT \
              -DU_COMMON_IMPLEMENTATION \
              -DU_I18N_IMPLEMENTATION \
              -DU_STATIC_IMPLEMENTATION \
              -DU_DISABLE_RENAMING


LOCAL_EXPORT_C_INCLUDES := LOCAL_C_INCLUDES

BOOST_CFLAGS := -DBOOST_LOCALE_NO_POSIX_BACKEND -DBOOST_LOCALE_NO_WINAPI_BACKEND -DBOOST_LOCALE_WITH_ICU

LOCAL_CFLAGS += $(BOOST_CFLAGS) $(ICU_CFLAGS)
LOCAL_CPPFLAGS += $(BOOST_CFLAGS) $(ICU_CFLAGS)

LOCAL_SRC_FILES += $(wildcard $(BOOST_LIBS_DIR)/atomic/src/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/chrono/src/*.cpp ) \
        $(BOOST_LIBS_DIR)/container/src/alloc_lib.c \
        $(wildcard $(BOOST_LIBS_DIR)/container/src/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/date_time/src/gregorian/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/date_time/src/posix_time/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/filesystem/src/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/locale/src/encoding/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/locale/src/icu/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/locale/src/shared/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/locale/src/std/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/locale/src/util/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/smart_ptr/src/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/system/src/*.cpp ) \
        $(wildcard $(BOOST_LIBS_DIR)/thread/src/*.cpp ) \
        $(BOOST_LIBS_DIR)/thread/src/pthread/once_atomic.cpp \
        $(BOOST_LIBS_DIR)/thread/src/pthread/thread.cpp

LOCAL_SRC_FILES += $(wildcard $(ICU_DIR)/source/common/*.c ) \
        $(wildcard $(ICU_DIR)/source/common/*.cpp ) \
        $(wildcard $(ICU_DIR)/source/common/unicode/*.c ) \
        $(wildcard $(ICU_DIR)/source/i18n/*.c ) \
        $(wildcard $(ICU_DIR)/source/i18n/*.cpp ) \
        $(ICU_DIR)/source/stubdata/stubdata.c



include $(BUILD_SHARED_LIBRARY)

