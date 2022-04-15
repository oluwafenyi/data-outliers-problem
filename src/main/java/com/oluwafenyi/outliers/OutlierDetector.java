package com.oluwafenyi.outliers;

import com.oluwafenyi.outliers.strategy.IOutlierDetectionStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * This class employs the strategy design pattern to enable the use of different algorithms to find outliers in a set
 * of data points.
 */
public class OutlierDetector {
    /**
     * An instance of {@link IOutlierDetectionStrategy}
     */
    IOutlierDetectionStrategy detectionStrategy;
    /**
     * A store for all data points processed by the outlier detector
     */
    List<DataPoint> historicalDataPoints = new LinkedList<>();

    /**
     * Constructor for creating am {@link OutlierDetector} instance
     * @param detectionStrategy instance of {@link IOutlierDetectionStrategy}, strategy employed to detect outliers
     */
    public OutlierDetector(IOutlierDetectionStrategy detectionStrategy) {
        this.detectionStrategy = detectionStrategy;
    }

    /**
     * This method provides a way to switch strategies on an existing {@link OutlierDetector} object
     * @param strategy instance of {@link IOutlierDetectionStrategy}
     */
    public void setStrategy(IOutlierDetectionStrategy strategy) {
        this.detectionStrategy = strategy;
    }

    /**
     * This method calls on the {@link IOutlierDetectionStrategy}.getOutliers() implementation, the data points passed
     * in are persisted for subsequent evaluations of outliers
     * @param dataPoints a list of data points, non-null
     * @return a list of outliers in the given data point
     */
    public List<DataPoint> getOutliers(List<DataPoint> dataPoints) {
        List<DataPoint> outliers = this.detectionStrategy.getOutliers(this.historicalDataPoints, dataPoints);
        this.historicalDataPoints.addAll(dataPoints);
        return outliers;
    }

    /**
     * This method calls on the {@link IOutlierDetectionStrategy}.removeOutliers() implementation
     * @param dataPoints a list of data points, non-null
     * @param outliers a list of outliers to be removed from the list of data points, non-null
     * @return a list of data points without the outliers
     */
    public List<DataPoint> removeOutliers(List<DataPoint> dataPoints, List<DataPoint> outliers) {
        return this.detectionStrategy.removeOutliers(dataPoints, outliers);
    }
}
