package com.oluwafenyi.outliers;

import com.oluwafenyi.outliers.strategy.IOutlierDetectionStrategy;

import java.util.LinkedList;
import java.util.List;

public class OutlierDetector {
    IOutlierDetectionStrategy detectionStrategy;
    List<DataPoint> historicalDataPoints = new LinkedList<>();

    public OutlierDetector(IOutlierDetectionStrategy detectionStrategy) {
        this.detectionStrategy = detectionStrategy;
    }

    public void setStrategy(IOutlierDetectionStrategy strategy) {
        this.detectionStrategy = strategy;
    }

    public List<DataPoint> getOutliers(List<DataPoint> dataPoints) {
        this.historicalDataPoints.addAll(dataPoints);
        return this.detectionStrategy.getOutliers(this.historicalDataPoints, dataPoints);
    }

    public List<DataPoint> removeOutliers(List<DataPoint> dataPoints, List<DataPoint> outliers) {
        return this.detectionStrategy.removeOutliers(dataPoints, outliers);
    }
}
