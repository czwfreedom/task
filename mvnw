#!/bin/sh
# Maven Wrapper Start Script

# 强制使用 Java 25
export JAVA_HOME="/Users/fordream/Library/Java/JavaVirtualMachines/corretto-25.0.4/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

# 定位项目根目录
PRG="$0"
while [ -h "$PRG" ]; do
  ls=$(ls -ld "$PRG")
  link=$(expr "$ls" : '.*-> \(.*\)$')
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG="$(dirname "$PRG")/$link"
  fi
done
SAVED="$(pwd)"
cd "$(dirname "$PRG")/" >/dev/null
APP_HOME="$(pwd -P)"
cd "$SAVED" >/dev/null

# Maven Wrapper 配置
MAVEN_WRAPPER_PROPERTIES="$APP_HOME/.mvn/wrapper/maven-wrapper.properties"
MAVEN_WRAPPER_JAR="$APP_HOME/.mvn/wrapper/maven-wrapper.jar"

# 首次运行自动下载 Maven
if [ ! -d "$HOME/.m2/wrapper/dists" ] || [ -z "$(ls -A "$HOME/.m2/wrapper/dists" 2>/dev/null)" ]; then
  echo "==> First run: downloading Maven distribution..."
fi

exec "$JAVA_HOME/bin/java" \
  -classpath "$MAVEN_WRAPPER_JAR" \
  -Dmaven.multiModuleProjectDirectory="$APP_HOME" \
  -Dmaven.wrapper.properties="$MAVEN_WRAPPER_PROPERTIES" \
  org.apache.maven.wrapper.MavenWrapperMain \
  "$@"
