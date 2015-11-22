# Build OpenApoc for Android

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := openapoc

OPENAPOC_PATH := $(LOCAL_PATH)/../OpenApoc

# Hopefully, these will set their includes right...
LOCAL_SHARED_LIBRARIES := allegro physfs icuuc55 tinyxml2

# Include path for version.h and glm
LOCAL_C_INCLUDES += $(LOCAL_PATH) \
					$(OPENAPOC_PATH) \
					$(OPENAPOC_PATH)/dependencies/glm \
					$(OPENAPOC_PATH)/dependencies/allegro/addons/physfs

LOCAL_CXX_INCLUDES += $(LOCAL_PATH)

LOCAL_ARM_MODE := arm

OPENAPOC_FLAGS := -DOPENAPOC_GLES -DRENDERERS=\"GLES_3_0:GLES_2_0\" \
 				-Wno-inconsistent-missing-override -DBROKEN_THREAD_LOCAL

LOCAL_CFLAGS += $(OPENAPOC_FLAGS)

LOCAL_CXXFLAGS += $(OPENAPOC_FLAGS) -std=gnu++11 -fexceptions

# There's actually a .c file in there somewhere...
LOCAL_SRC_FILES := $(wildcard $(OPENAPOC_PATH)/forms/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/*.c) \
			$(wildcard $(OPENAPOC_PATH)/framework/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/imageloader/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/render/gles20/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/render/gles30/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/sound/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/framework/ThreadPool/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/apocresources/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/base/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/city/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/debugtools/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/general/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/resources/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/rules/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/tileview/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/game/ufopaedia/*.cpp) \
			$(wildcard $(OPENAPOC_PATH)/library/*.cpp)

# Link against these libraries for good measure
LOCAL_LDLIBS := -lEGL -lGLESv1_CM -lGLESv2 -lGLESv3 -lOpenSLES -llog -landroid -lm

include $(BUILD_SHARED_LIBRARY)