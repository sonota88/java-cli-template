require "rake/testtask"

task :default => :package

SRC_FILES = Dir.glob("./src/**/*").to_a

ARTIFACT_ID = "sample_a"
JAR_FILE = "target/#{ARTIFACT_ID}-0.0.1-SNAPSHOT-jar-with-dependencies.jar"

file JAR_FILE => SRC_FILES do
  sh %( ./build_mvn.sh package )
end

task :package => JAR_FILE
