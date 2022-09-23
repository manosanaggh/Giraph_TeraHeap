#!/usr/bin/env bash

# Add tools.jar for compilation purposes
cd /usr/lib/jvm/java-8-kolokasis/build/linux-x86_64-normal-server-release/
sudo mkdir -p lib
sudo cp /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.x86_64/lib/tools.jar ./lib/
cd -

# Compile giraph
mvn -Phadoop_yarn -Dhadoop.version=2.4.0 -DskipTests -Dcheckstyle.skip clean package install
