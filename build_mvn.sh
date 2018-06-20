#!/bin/bash

_get_project_dir() {
  local real_path="$(readlink --canonicalize "$0")"
  (
    cd "$(dirname "$real_path")"
    pwd
  )
}


# --------------------------------

_build_mvn() {
  $MVN_CMD clean
  $MVN_CMD compile
}

_package_mvn() {
  $MVN_CMD clean
  $MVN_CMD package -Dmaven.test.skip=true

  echo "----"
  ls -l target/*.jar
  echo "----"
  ls -lh target/*.jar
}


# --------------------------------
# Main

CURRENT_DIR=$(pwd)
PROJECT_DIR=$(_get_project_dir)
cd $PROJECT_DIR

source "${PROJECT_DIR}/common.sh"

case "$1" in
  build)
    _build_mvn
    ;;
  package)
    _package_mvn
    ;;
  *)
    echo "invalid argument" >&2
    ;;
esac
