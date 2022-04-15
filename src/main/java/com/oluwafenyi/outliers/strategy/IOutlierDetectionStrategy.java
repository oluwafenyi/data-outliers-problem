package com.oluwafenyi.outliers.strategy;

import com.oluwafenyi.outliers.DataPoint;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public interface IOutlierDetectionStrategy {
    List<DataPoint> getOutliers(List<DataPoint> historicalDataPoints, List<DataPoint> dataPoints);

    List<DataPoint> removeOutliers(List<DataPoint> dataPoints, List<DataPoint> outliers);
}
