# 概要

TODO


# 使い方

TODO


# ビルド

```
コンパイル
./build_mvn.sh compile

パッケージ（jar）作成
./build_mvn.sh package
```


# fatjar のビルド

```sh
./gradlew shadowJar -DmainClassName=sample.Main
  # => build/libs/xxx-all.jar
```
