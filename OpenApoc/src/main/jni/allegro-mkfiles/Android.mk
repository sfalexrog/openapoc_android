# Build Allegro library from OpenApoc/dependencies/allegro

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := allegro

# Assume that Allegro libraries are actually at OpenApoc/dependencies/allegro
ALLEGRO_PATH := $(LOCAL_PATH)/../OpenApoc/dependencies/allegro

# Required for physfs add-on (assuming it's in OpenApoc/dependencies/physfs)
PHYSFS_PATH := $(LOCAL_PATH)/../OpenApoc/dependencies/physfs

# Provide all required include paths
LOCAL_C_INCLUDES := $(ALLEGRO_PATH)/include \
					$(ALLEGRO_PATH)/addons/primitives \
					$(ALLEGRO_PATH)/addons/image \
					$(ALLEGRO_PATH)/addons/font \
					$(ALLEGRO_PATH)/addons/audio \
					$(ALLEGRO_PATH)/addons/acodec \
					$(ALLEGRO_PATH)/addons/memfile \
					$(ALLEGRO_PATH)/addons/color \
					$(ALLEGRO_PATH)/addons/main \
					$(ALLEGTO_PATH)/addons/physfs \
					$(LOCAL_PATH)/include

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_C_INCLUDES)

LOCAL_ARM_MODE := arm

# These flags are taken from the makefiles built using the "official" Allegro for Android way.
ALLEGRO_CFLAGS := -DGL_GLEXT_PROTOTYPES -fPIC -DANDROID -DDEBUGMODE=1 \
				-DD3D_DEBUG_INFO -DALLEGRO_SRC  -DALLEGRO_PRIMITIVES_SRC \
				-DALLEGRO_IIO_SRC  -DALLEGRO_FONT_SRC  -DALLEGRO_KCM_AUDIO_SRC \
				-DALLEGRO_ACODEC_SRC   -DALLEGRO_COLOR_SRC  -DALLEGRO_MEMFILE_SRC \
				-DALLEGRO_SRC    -DALLEGRO_LIB_BUILD -Dallegro_monolith_EXPORTS

LOCAL_CFLAGS += $(ALLEGRO_CFLAGS)

LOCAL_CXXFLAGS += $(ALLEGRO_CFLAGS)

LOCAL_SRC_FILES := $(ALLEGRO_PATH)/addons/main/generic_main.c \
		$(ALLEGRO_PATH)/addons/primitives/high_primitives.c \
		$(ALLEGRO_PATH)/addons/primitives/prim_util.c \
		$(ALLEGRO_PATH)/addons/primitives/point_soft.c \
		$(ALLEGRO_PATH)/addons/primitives/prim_directx.cpp \
		$(ALLEGRO_PATH)/addons/primitives/triangulator.c \
		$(ALLEGRO_PATH)/addons/primitives/primitives.c \
		$(ALLEGRO_PATH)/addons/primitives/polygon.c \
		$(ALLEGRO_PATH)/addons/primitives/polyline.c \
		$(ALLEGRO_PATH)/addons/primitives/prim_opengl.c \
		$(ALLEGRO_PATH)/addons/primitives/line_soft.c \
		$(ALLEGRO_PATH)/addons/primitives/prim_soft.c \
		$(ALLEGRO_PATH)/addons/color/color.c \
		$(ALLEGRO_PATH)/addons/memfile/memfile.c \
		$(ALLEGRO_PATH)/addons/acodec/helper.c \
		$(ALLEGRO_PATH)/addons/acodec/wav.c \
		$(ALLEGRO_PATH)/addons/acodec/acodec.c \
		$(ALLEGRO_PATH)/addons/acodec/voc.c \
		$(ALLEGRO_PATH)/addons/audio/kcm_instance.c \
		$(ALLEGRO_PATH)/addons/audio/kcm_stream.c \
		$(ALLEGRO_PATH)/addons/audio/kcm_sample.c \
		$(ALLEGRO_PATH)/addons/audio/opensl.c \
		$(ALLEGRO_PATH)/addons/audio/kcm_voice.c \
		$(ALLEGRO_PATH)/addons/audio/audio.c \
		$(ALLEGRO_PATH)/addons/audio/recorder.c \
		$(ALLEGRO_PATH)/addons/audio/audio_io.c \
		$(ALLEGRO_PATH)/addons/audio/kcm_mixer.c \
		$(ALLEGRO_PATH)/addons/audio/kcm_dtor.c \
		$(ALLEGRO_PATH)/addons/font/fontbmp.c \
		$(ALLEGRO_PATH)/addons/font/font.c \
		$(ALLEGRO_PATH)/addons/font/text.c \
		$(ALLEGRO_PATH)/addons/font/stdfont.c \
		$(ALLEGRO_PATH)/addons/image/bmp.c \
		$(ALLEGRO_PATH)/addons/image/iio.c \
		$(ALLEGRO_PATH)/addons/image/tga.c \
		$(ALLEGRO_PATH)/addons/image/dds.c \
		$(ALLEGRO_PATH)/addons/image/android.c \
		$(ALLEGRO_PATH)/addons/image/pcx.c \
		$(ALLEGRO_PATH)/addons/physfs/a5_physfs.c \
		$(ALLEGRO_PATH)/addons/physfs/a5_physfs_dir.c \
		$(ALLEGRO_PATH)/src/convert.c \
		$(ALLEGRO_PATH)/src/monitor.c \
		$(ALLEGRO_PATH)/src/mousenu.c \
		$(ALLEGRO_PATH)/src/shader.c \
		$(ALLEGRO_PATH)/src/joynu.c \
		$(ALLEGRO_PATH)/src/config.c \
		$(ALLEGRO_PATH)/src/transformations.c \
		$(ALLEGRO_PATH)/src/threads.c \
		$(ALLEGRO_PATH)/src/pixels.c \
		$(ALLEGRO_PATH)/src/unix/utime.c \
		$(ALLEGRO_PATH)/src/unix/uxthread.c \
		$(ALLEGRO_PATH)/src/unix/ufdwatch.c \
		$(ALLEGRO_PATH)/src/system.c \
		$(ALLEGRO_PATH)/src/tri_soft.c \
		$(ALLEGRO_PATH)/src/memdraw.c \
		$(ALLEGRO_PATH)/src/inline.c \
		$(ALLEGRO_PATH)/src/path.c \
		$(ALLEGRO_PATH)/src/memblit.c \
		$(ALLEGRO_PATH)/src/debug.c \
		$(ALLEGRO_PATH)/src/fshook.c \
		$(ALLEGRO_PATH)/src/mouse_cursor.c \
		$(ALLEGRO_PATH)/src/bitmap_type.c \
		$(ALLEGRO_PATH)/src/memory.c \
		$(ALLEGRO_PATH)/src/android/android_sensors.c \
		$(ALLEGRO_PATH)/src/android/android_keyboard.c \
		$(ALLEGRO_PATH)/src/android/android_system.c \
		$(ALLEGRO_PATH)/src/android/android_input_stream.c \
		$(ALLEGRO_PATH)/src/android/jni_helpers.c \
		$(ALLEGRO_PATH)/src/android/android_touch.c \
		$(ALLEGRO_PATH)/src/android/android_display.c \
		$(ALLEGRO_PATH)/src/android/android_image.c \
		$(ALLEGRO_PATH)/src/android/android_apk_file.c \
		$(ALLEGRO_PATH)/src/android/android_joystick.c \
		$(ALLEGRO_PATH)/src/android/android_mouse.c \
		$(ALLEGRO_PATH)/src/exitfunc.c \
		$(ALLEGRO_PATH)/src/misc/vector.c \
		$(ALLEGRO_PATH)/src/misc/aatree.c \
		$(ALLEGRO_PATH)/src/misc/list.c \
		$(ALLEGRO_PATH)/src/misc/bstrlib.c \
		$(ALLEGRO_PATH)/src/libc.c \
		$(ALLEGRO_PATH)/src/math.c \
		$(ALLEGRO_PATH)/src/bitmap_lock.c \
		$(ALLEGRO_PATH)/src/bitmap_pixel.c \
		$(ALLEGRO_PATH)/src/dtor.c \
		$(ALLEGRO_PATH)/src/fshook_stdio.c \
		$(ALLEGRO_PATH)/src/evtsrc.c \
		$(ALLEGRO_PATH)/src/bitmap_io.c \
		$(ALLEGRO_PATH)/src/bitmap.c \
		$(ALLEGRO_PATH)/src/touch_input.c \
		$(ALLEGRO_PATH)/src/allegro.c \
		$(ALLEGRO_PATH)/src/haptic.c \
		$(ALLEGRO_PATH)/src/file_stdio.c \
		$(ALLEGRO_PATH)/src/timernu.c \
		$(ALLEGRO_PATH)/src/fullscreen_mode.c \
		$(ALLEGRO_PATH)/src/file_slice.c \
		$(ALLEGRO_PATH)/src/opengl/ogl_bitmap.c \
		$(ALLEGRO_PATH)/src/opengl/ogl_render_state.c \
		$(ALLEGRO_PATH)/src/opengl/ogl_fbo.c \
		$(ALLEGRO_PATH)/src/opengl/ogl_draw.c \
		$(ALLEGRO_PATH)/src/opengl/extensions.c \
		$(ALLEGRO_PATH)/src/opengl/ogl_shader.c \
		$(ALLEGRO_PATH)/src/opengl/ogl_lock.c \
		$(ALLEGRO_PATH)/src/opengl/ogl_lock_es.c \
		$(ALLEGRO_PATH)/src/opengl/ogl_display.c \
		$(ALLEGRO_PATH)/src/drawing.c \
		$(ALLEGRO_PATH)/src/display_settings.c \
		$(ALLEGRO_PATH)/src/events.c \
		$(ALLEGRO_PATH)/src/file.c \
		$(ALLEGRO_PATH)/src/tls.c \
		$(ALLEGRO_PATH)/src/blenders.c \
		$(ALLEGRO_PATH)/src/keybdnu.c \
		$(ALLEGRO_PATH)/src/utf8.c \
		$(ALLEGRO_PATH)/src/display.c \
		$(ALLEGRO_PATH)/src/linux/ljoynu.c \
		$(ALLEGRO_PATH)/src/bitmap_draw.c

# Better depend on this one for physfs add-on
LOCAL_SHARED_LIBRARIES := physfs

# Taken from Allegro makefiles
LOCAL_LDLIBS := -lEGL -lGLESv1_CM -lGLESv2 -lOpenSLES -llog

include $(BUILD_SHARED_LIBRARY)