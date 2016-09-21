# Uncomment this if you're using STL in your project
# See CPLUSPLUS-SUPPORT.html in the NDK documentation for more information
APP_STL := c++_static
APP_CPPFLAGS += -frtti -fexceptions
APP_LDFLAGS += -latomic

# Use latest available clang toolchain
NDK_TOOLCHAIN_VERSION := clang

APP_OPTIM := release

# Build for 32-bit architectures by default (64-bit ones are not supported by Allegro5)
ifeq ($(BUILD_ARCH_LIST),)
    APP_ABI := armeabi-v7a
     #x86
else
    APP_ABI := $(BUILD_ARCH_LIST)
endif

# APP_PLATFORM should not be set any higher to preserve compatibility
APP_PLATFORM := android-19
# Next line is needed for compilation on windows; remove on other systems with
# sane values for command line length.
# APP_SHORT_COMMANDS := true