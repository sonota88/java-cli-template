ARG_DELIM="\x1f"

# MVN_CMD="/path/to/apache-maven-x.x.x/bin/mvn"
# MVN_CMD="mvn"
MVN_CMD="./mvnw"

# GRADLE_CMD="/path/to/gradle-x.x/bin/gradle"
GRADLE_CMD="./gradlew"

_build_args(){
  local args="$1"
  shift

  for arg in "$@"; do
    args=$(printf -- "${args}${ARG_DELIM}${arg}")
  done

  printf -- "$args"
}

_exec_mvn() {
  local main_class="$1"; shift
  local args=$(_build_args "$@")

  export RUN_MODE=mvn_exec

  $MVN_CMD exec:java \
    --quiet \
    "-Dexec.mainClass=${main_class}" \
    "-Dexec.args=${args}"
}

_build_gradle() {
  $GRADLE_CMD clean
  $GRADLE_CMD build
}

_exec_gradle() {
  local main_class="$1"; shift
  local args=$(_build_args "$@")

  export RUN_MODE=gradle_exec

  $GRADLE_CMD run \
    --quiet \
    "-DmainClassName=${main_class}" \
    "-Pargs=${args}"
}
