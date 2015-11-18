# Build a physfs shared library

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := physfs

# Assume physfs is at OpenApoc/dependencies/physfs
PHYSFS_PATH := $(LOCAL_PATH)/../OpenApoc/dependencies/physfs

LOCAL_C_INCLUDES := $(PHYSFS_PATH)/src

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_C_INCLUDES)

LOCAL_ARM_MODE := arm

PHYSFS_CFLAGS := -DPHYSFS_SUPPORTS_ISO9660

LOCAL_CFLAGS += $(PHYSFS_CFLAGS)

LOCAL_CXXFLAGS += $(PHYSFS_CFLAGS)

LOCAL_SRC_FILES := $(PHYSFS_PATH)/src/archiver_dir.c \
		$(PHYSFS_PATH)/src/archiver_grp.c \
		$(PHYSFS_PATH)/src/archiver_hog.c \
		$(PHYSFS_PATH)/src/archiver_iso9660.c \
		$(PHYSFS_PATH)/src/archiver_lzma.c \
		$(PHYSFS_PATH)/src/archiver_mvl.c \
		$(PHYSFS_PATH)/src/archiver_qpak.c \
		$(PHYSFS_PATH)/src/archiver_slb.c \
		$(PHYSFS_PATH)/src/archiver_unpacked.c \
		$(PHYSFS_PATH)/src/archiver_wad.c \
		$(PHYSFS_PATH)/src/archiver_zip.c \
		$(PHYSFS_PATH)/src/physfs.c \
		$(PHYSFS_PATH)/src/physfs_byteorder.c \
		$(PHYSFS_PATH)/src/physfs_unicode.c \
		$(PHYSFS_PATH)/src/platform_macosx.c \
		$(PHYSFS_PATH)/src/platform_posix.c \
		$(PHYSFS_PATH)/src/platform_unix.c

# This module does not seem to depend on anything...
#LOCAL_LDLIBS :=

include $(BUILD_SHARED_LIBRARY)