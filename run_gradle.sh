#!/bin/sh

# GRADLE_CMD="/path/to/gradle-x.x/bin/gradle"
GRADLE_CMD="gradle"

_get_project_dir() {
  if [ -L $0 ] ; then
    # シンボリックリンク
    dirname $(readlink --canonicalize $0)
  else
    echo $(cd $(dirname $0); pwd)
  fi

  return 0
}

_build() {
  $GRADLE_CMD clean
  $GRADLE_CMD build
}

_exec() {
  $GRADLE_CMD run \
    --quiet \
    -DmainClassName=sample.Main \
    "-Pargs=$*"
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
