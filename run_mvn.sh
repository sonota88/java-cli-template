#!/bin/bash

_get_project_dir() {
  local real_path="$(readlink --canonicalize "$0")"
  (
    cd "$(dirname "$real_path")"
    pwd
  )
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
  _build_mvn
else
  _exec "$CURRENT_DIR" "$PROJECT_DIR" "$@"
fi
