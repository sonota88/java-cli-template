#!/bin/bash

_get_project_dir() {
  local real_path="$(readlink --canonicalize "$0")"
  (
    cd "$(dirname "$real_path")"
    pwd
  )
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
  _exec_mvn "sample.SubcmdMain" "${CURRENT_DIR}" "${PROJECT_DIR}" "$@"
fi
