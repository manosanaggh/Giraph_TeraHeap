/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.giraph.aggregators.matrix.dense;

import org.apache.giraph.aggregators.AggregatorUsage;
import org.apache.giraph.aggregators.matrix.MatrixSumAggregator;
import org.apache.giraph.master.MasterAggregatorUsage;
import org.apache.giraph.worker.WorkerAggregatorUsage;

/**
 * The long dense matrix aggregator is used to register and aggregate long
 * dense matrices.
 */
public class LongDenseMatrixSumAggregator extends MatrixSumAggregator {
  /** Dense vector with a single entry */
  private LongDenseVector singletonVector = new LongDenseVector();

  /**
   * Create a new matrix aggregator with the given prefix name for the vector
   * aggregators.
   *
   * @param name the prefix for the row vector aggregators
   */
  public LongDenseMatrixSumAggregator(String name) {
    super(name);
  }

  /**
   * Register the long vector aggregators, one for each row of the matrix.
   *
   * @param numRows the number of rows
   * @param master the master to register the aggregators
   */
  public void register(int numRows, MasterAggregatorUsage master)
    throws InstantiationException, IllegalAccessException {
    for (int i = 0; i < numRows; ++i) {
      boolean success = master.registerAggregator(getRowAggregatorName(i),
          LongDenseVectorSumAggregator.class);
      if (!success) {
        throw new RuntimeException("Aggregator already registered");
      }
    }
  }

  /**
   * Add the given value to the entry specified.
   *
   * @param i the row
   * @param j the column
   * @param v the value
   * @param worker the worker to aggregate
   */
  public void aggregate(int i, int j, long v, WorkerAggregatorUsage worker) {
    singletonVector.setSingleton(j, v);
    worker.aggregate(getRowAggregatorName(i), singletonVector);
  }

  /**
   * Set the values of the matrix to the master specified. This is typically
   * used in the master, to build an external LongMatrix and only set it at
   * the end.
   *
   * @param matrix the matrix to set the values
   * @param master the master
   */
  public void setMatrix(LongDenseMatrix matrix, MasterAggregatorUsage master) {
    int numRows = matrix.getNumRows();
    for (int i = 0; i < numRows; ++i) {
      master.setAggregatedValue(getRowAggregatorName(i), matrix.getRow(i));
    }
  }

  /**
   * Read the aggregated values of the matrix.
   *
   * @param numRows the number of rows
   * @param aggUser the master or worker
   * @return the long matrix
   */
  public LongDenseMatrix getMatrix(int numRows, AggregatorUsage aggUser) {
    LongDenseMatrix matrix = new LongDenseMatrix(numRows, 1);
    for (int i = 0; i < numRows; ++i) {
      LongDenseVector vec = aggUser.getAggregatedValue(getRowAggregatorName(i));
      matrix.addRow(vec);
    }
    return matrix;
  }
}
