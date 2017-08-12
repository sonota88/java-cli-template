#!/bin/bash

# MVN_CMD="/path/to/apache-maven-x.x.x/bin/mvn"
MVN_CMD="mvn"
ARG_DELIM="\x1f"

_get_project_dir() {
  local real_path="$(readlink --canonicalize "$0")"
  (
    cd "$(dirname "$real_path")"
    pwd
  )
}

_build_args(){
  local args="$1"
  shift

  for arg in "$@"; do
    args=$(printf "${args}${ARG_DELIM}${arg}")
  done

  printf "$args"
}

_build() {
  $MVN_CMD clean
  $MVN_CMD compile
}

_exec() {
  local args=$(_build_args "$@")

  $MVN_CMD exec:java \
    --quiet \
    -Dexec.mainClass=sample.Main \
    "-Dexec.args=${args}"
}


# --------------------------------
# Main

CURRENT_DIR=$(pwd)
PROJECT_DIR=$(_get_project_dir)
cd $PROJECT_DIR

source "${PROJECT_DIR}/common.sh"

if [ "$1" = "build" ] ; then
  _build
else
  _exec "$CURRENT_DIR" "$PROJECT_DIR" "$@"
fi
