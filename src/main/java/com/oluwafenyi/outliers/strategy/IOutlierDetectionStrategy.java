package com.oluwafenyi.outliers.strategy;

import com.oluwafenyi.outliers.DataPoint;

import java.util.List;

/**
 * Outlier Detection Strategy Interface to support different implementations
 */
public interface IOutlierDetectionStrategy {
    /**
     * Finds outliers in a list of data points
     * @param historicalDataPoints historical data points for consideration
     * @param dataPoints list of data points
     * @return list of outliers in data point
     */
    List<DataPoint> getOutliers(List<DataPoint> historicalDataPoints, List<DataPoint> dataPoints);

    /**
     * Removes outliers from list of data points
     * @param dataPoints list of data points
     * @param outliers list of outlier data points
     * @return list of data points that are not in list of outliers
     */
    List<DataPoint> removeOutliers(List<DataPoint> dataPoints, List<DataPoint> outliers);
}
