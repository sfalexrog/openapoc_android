# openapoc_android [![Build Status](https://travis-ci.org/sfalexrog/openapoc_android.svg?branch=master)](https://travis-ci.org/sfalexrog/openapoc_android)
Android Studio/gradle project for [OpenApoc](https://github.com/pmprog/OpenApoc) Android port.

# BIG CAVEAT

Much like OpenApoc, this port is in early stages of development. Right now the most you can do is run the app and watch the cityscape.

## Smaller caveats

Currently, the project only works on 32-bit architectures. Will probably be fixed in next versions of Allegro library.

armv6 is not supported.

Native code compilation is a bunch of ugly hacks.

# Usage

This section explains how to build yourself a copy of OpenApoc for Android

## Requirements

 - Android SDK
 - Android NDK (built using r10e and NDK bundle from Android SDK)
 - Android Studio (not strictly required, but could help anyway)

## Getting the Source

This project uses submodules to manage its dependencies. It also relies on OpenApoc project doing the same, so in order to build the project you have to run

    $ git submodule --init --recursive

in the project root. This command should fetch all required submodules.

## Building

Just run

    $ ./gradlew assemble

in order to build the apk. The resulting apk will be in `OpenApoc/build/outputs/apk`.

## Running

Currently OpenApoc expects all the data to be in `/sdcard/openapoc` folder of your device. The logfile will be written to `/sdcard/openapoc/data/openapoc_log.txt`. Note that you should put your cd.iso to `/sdcard/openapoc/cd.iso`, all contents of OpenApoc's `data` folder to `/sdcard/openapoc`.
