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
  echo "Install Maven Wrapper"
  mvn -N io.takari:maven:0.7.7:wrapper -Dmaven=3.6.3
}

_mvn_compile() {
  $MVN_CMD clean
  $MVN_CMD compile
}

_mvn_package() {
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
  setup)
    setup
    ;;
  compile)
    _mvn_compile
    ;;
  package)
    _mvn_package
    ;;
  *)
    echo "invalid argument" >&2
    ;;
esac
