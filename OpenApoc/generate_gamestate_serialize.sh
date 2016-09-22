#!/usr/bin/bash
BASEDIR=$(dirname $0)
OPENAPOC_PATH="${BASEDIR}/src/main/jni/OpenApoc"
GSGEN_PATH="${OPENAPOC_PATH}/tools/gamestate_serialize_gen"
COMPILE_OPTS="-I${OPENAPOC_PATH} -DPUGIXML_HEADER_ONLY -lboost_program_options"

GAMESTATE_PATH="${OPENAPOC_PATH}/game/state"
GAMESTATE_XML="${GAMESTATE_PATH}/gamestate_serialize.xml"
GAMESTATE_CPP="${GAMESTATE_PATH}/gamestate_serialize_generated.cpp"
GAMESTATE_H="${GAMESTATE_PATH}/gamestate_serialize_generated.h"

echo Compiling gamestate serialization generator...
g++ ${GSGEN_PATH}/main.cpp ${COMPILE_OPTS} -o ${BASEDIR}/gsgen
echo Generating gamestate serialization...
${BASEDIR}/gsgen -x ${GAMESTATE_XML} -o ${GAMESTATE_CPP} -h ${GAMESTATE_H}
echo All done!
