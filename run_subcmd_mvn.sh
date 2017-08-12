#!/bin/sh

# MVN_CMD="/path/to/apache-maven-x.x.x/bin/mvn"
MVN_CMD="mvn"

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
