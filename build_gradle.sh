#!/bin/bash

_get_project_dir() {
  local real_path="$(readlink --canonicalize "$0")"
  (
    cd "$(dirname "$real_path")"
    pwd
  )
}


# --------------------------------

setup(){
  echo <<'__EOF'
gradle wrapper
__EOF
}

_package() {
  $GRADLE_CMD clean
  $GRADLE_CMD shadowJar -DmainClassName=sample.Main

  echo "----"
  ls -l build/libs/*.jar
  echo "----"
  ls -lh build/libs/*.jar
}


# --------------------------------
# Main

CURRENT_DIR=$(pwd)
PROJECT_DIR=$(_get_project_dir)
cd $PROJECT_DIR

source "${PROJECT_DIR}/common.sh"

case "$1" in
  setup)
    setup
    ;;
  package)
    _package
    ;;
  *)
    echo "invalid argument" >&2
    ;;
esac
