# Build OpenApoc for Android

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := openapoc

OPENAPOC_PATH := $(LOCAL_PATH)/../OpenApoc

# Hopefully, these will set their includes right...
LOCAL_SHARED_LIBRARIES := SDL2 physfs tinyxml2 boost

# Include path for version.h and glm
LOCAL_C_INCLUDES += $(LOCAL_PATH) \
					$(OPENAPOC_PATH) \
					$(OPENAPOC_PATH)/dependencies/glm \
					$(OPENAPOC_PATH)/dependencies/allegro/addons/physfs

LOCAL_CXX_INCLUDES += $(LOCAL_PATH)

LOCAL_ARM_MODE := arm

OPENAPOC_FLAGS := -DOPENAPOC_GLES -DRENDERERS=\"GLES_3_0\" \
 				-Wno-inconsistent-missing-override -DBROKEN_THREAD_LOCAL \
 				-DGLESWRAP_PLATFORM_EGL

LOCAL_CFLAGS += $(OPENAPOC_FLAGS)

LOCAL_CXXFLAGS += $(OPENAPOC_FLAGS) -std=gnu++11 -fexceptions

# There's actually a .c file in there somewhere...
LOCAL_SRC_FILES := $(wildcard $(OPENAPOC_PATH)/forms/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/*.c) \
			$(wildcard $(OPENAPOC_PATH)/framework/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/apocresources/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/fs/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/imageloader/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/musicloader/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/render/gles30_v2/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/sampleloader/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/serialization/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/serialization/providers/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/sound/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/ThreadPool/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/video/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/state/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/state/base/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/state/battle/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/state/city/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/state/rules/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/state/tileview/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ui/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ui/base/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ui/battle/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ui/city/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ui/debugtools/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ui/general/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ui/tileview/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ui/ufopaedia/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/library/*.cpp)

# Compile in some dependencies

PUGIXML_DIR := $(OPENAPOC_PATH)/dependencies/pugixml

SMACKER_DIR := $(OPENAPOC_PATH)/dependencies/libsmacker

LODEPNG_DIR := $(OPENAPOC_PATH)/dependencies/lodepng

MINIZIP_DIR := $(OPENAPOC_PATH)/dependencies/miniz

LOCAL_SRC_FILES += $(PUGIXML_DIR)/src/pugixml.cpp \
        $(SMACKER_DIR)/driver.c \
        $(SMACKER_DIR)/smacker.c \
        $(SMACKER_DIR)/smk_bitstream.c \
        $(SMACKER_DIR)/smk_hufftree.c \
        $(LODEPNG_DIR)/lodepng.cpp \
        $(MINIZIP_DIR)/miniz.c


# Link against these libraries for good measure
LOCAL_LDLIBS := -lEGL -lGLESv1_CM -lGLESv2 -lGLESv3 -lOpenSLES -llog -landroid -lm

include $(BUILD_SHARED_LIBRARY)