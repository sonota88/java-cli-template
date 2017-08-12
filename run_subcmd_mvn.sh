#!/bin/sh

# MVN_CMD="/path/to/apache-maven-x.x.x/bin/mvn"
MVN_CMD="mvn"

_get_project_dir() {
  local real_path="$(readlink --canonicalize "$0")"
  (
    cd "$(dirname "$real_path")"
    pwd
  )
}

_build() {
  $MVN_CMD clean
  $MVN_CMD compile
}

_exec() {
  $MVN_CMD exec:java \
    --quiet \
    -Dexec.mainClass=sample.SubcmdMain \
    "-Dexec.args=$*"
}


# --------------------------------
# Main

CURRENT_DIR=$(pwd)
PROJECT_DIR=$(_get_project_dir)
cd $PROJECT_DIR

if [ "$1" = "build" ] ; then
  _build
else
  _exec "${CURRENT_DIR}" "${PROJECT_DIR}" "$@"
fi
