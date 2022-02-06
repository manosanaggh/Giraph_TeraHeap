#!/usr/bin/env bash

INSTALL_DIR=""

# Download Hadoop
wget https://archive.apache.org/dist/hadoop/common/hadoop-2.4.0/hadoop-2.4.0.tar.gz
tar xf hadoop-2.4.0.tar.gz
sudo chown -R $(whoami) hadoop-2.4.0
rm hadoop-2.4.0.tar.gz

# Download Giraph
wget https://dlcdn.apache.org/giraph/giraph-1.2.0/giraph-dist-1.2.0-hadoop2-src.tar.gz
tar xf giraph-dist-1.2.0-hadoop2-src.tar.gz
sudo chown -R $(whoami) giraph-1.2.0-hadoop2
rm giraph-dist-1.2.0-hadoop2-src.tar.gz
