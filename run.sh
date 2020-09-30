print_project_dir() {
  (
    cd "$(dirname "$0")"
    pwd
  )
}

CURRENT_DIR="$(pwd)"
PROJECT_DIR="$(print_project_dir)"

. ${PROJECT_DIR}/common.sh

java -jar \
  ${PROJECT_DIR}/target/sample_a-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
  "$@"
