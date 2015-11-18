LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# Assume tinyxml is at OpenApoc/dependencies/tinyxml2
TINYXML_PATH := $(LOCAL_PATH)/../OpenApoc/dependencies/tinyxml2

LOCAL_MODULE := tinyxml2

LOCAL_C_INCLUDES := $(TINYXML_PATH)

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_C_INCLUDES)

LOCAL_ARM_MODE := arm

LOCAL_SRC_FILES := $(TINYXML_PATH)/tinyxml2.cpp

include $(BUILD_SHARED_LIBRARY)