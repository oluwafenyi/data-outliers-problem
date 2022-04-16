package com.oluwafenyi.outliers;

import com.oluwafenyi.outliers.strategy.IOutlierDetectionStrategy;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final List<DataPoint> historicalDataPoints = new LinkedList<>();

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
     * Add data points for consideration
     * @param dataPoints list of data points
     */
    public void addDataPoints(List<DataPoint> dataPoints) {
        this.historicalDataPoints.addAll(dataPoints);
    }

    /**
     * This method calls on the {@link IOutlierDetectionStrategy}.getOutliers() implementation
     * @return a list of outliers in the given data point
     */
    public List<DataPoint> getOutliers() {
        return this.detectionStrategy.getOutliers(this.historicalDataPoints);
    }

    /**
     * Filters outliers up to date specified and calls on the {@link IOutlierDetectionStrategy}.getOutliers() implementation
     * @param date date value
     * @return list of outliers up to specified date
     */
    public List<DataPoint> getOutliersUpToDate(LocalDate date) {
        List<DataPoint> dataPointsToConsider = this.historicalDataPoints.stream().filter(dataPoint -> dataPoint.date.isBefore(date) || dataPoint.date.isEqual(date)).collect(Collectors.toList());
        return this.detectionStrategy.getOutliers(dataPointsToConsider);
    }

    /**
     * This method calls on the {@link IOutlierDetectionStrategy}.removeOutliers() implementation
     * @param outliers a list of outliers to be removed from the list of data points, non-null
     * @return a list of data points without the outliers
     */
    public List<DataPoint> removeOutliers(List<DataPoint> outliers) {
        return this.detectionStrategy.removeOutliers(this.historicalDataPoints, outliers);
    }

    /**
     * This method calls on the {@link IOutlierDetectionStrategy}.removeOutliers() implementation, and filters results up to specified date
     * @param outliers list of data points to remove
     * @param date date value
     * @return list of historical data points that are not outliers up to specified date
     */
    public List<DataPoint> removeOutliersUptoDate(List<DataPoint> outliers, LocalDate date) {
        return this.detectionStrategy.removeOutliers(this.historicalDataPoints, outliers).stream().filter(dataPoint -> dataPoint.date.isBefore(date) || dataPoint.date.isEqual(date)).collect(Collectors.toList());
    }
}
