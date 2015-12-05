#!/bin/sh

# MVN_CMD="/path/to/apache-maven-x.x.x/bin/mvn"
MVN_CMD="mvn"

_build() {
  $MVN_CMD clean
  $MVN_CMD compile
}

_exec() {
  $MVN_CMD exec:java \
    --quiet \
    -Dexec.mainClass=sample.Main \
    "-Dexec.args=$*"
}


# --------------------------------
# Main

if [ "$1" = "build" ] ; then
  _build
else
  _exec "$@"
fi
