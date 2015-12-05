#!/bin/sh

# GRADLE_CMD="/path/to/gradle-x.x/bin/gradle"
GRADLE_CMD="gradle"

_build() {
  $GRADLE_CMD clean
  $GRADLE_CMD build
}

_exec() {
  $GRADLE_CMD run \
    --quiet \
    -Dexec.mainClass=sample.Main \
    "-Pargs=$*"
}


# --------------------------------
# Main

if [ "$1" = "build" ] ; then
  _build
else
  _exec "$@"
fi
