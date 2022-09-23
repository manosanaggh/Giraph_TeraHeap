#!/usr/bin/env bash

hadoop jar ./giraph-examples/target/giraph-examples-1.2.0-hadoop2-for-hadoop-2.4.0-jar-with-dependencies.jar \
	org.apache.giraph.GiraphRunner \
	org.apache.giraph.examples.SimpleShortestPathsComputation \
	-vif org.apache.giraph.io.formats.JsonLongDoubleFloatDoubleVertexInputFormat \
	-vip tiny_graph.txt \
	-vof org.apache.giraph.io.formats.IdWithValueTextOutputFormat \
	-op output \
	-w 1 \
	-ca giraph.SplitMasterWorker=false
