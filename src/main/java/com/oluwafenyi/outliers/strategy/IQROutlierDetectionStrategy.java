package com.oluwafenyi.outliers.strategy;

import com.oluwafenyi.outliers.DataPoint;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Concrete implementation of {@link IOutlierDetectionStrategy}, this implementation uses the inter-quartile range to
 * determine outliers. A value of IQR * 1.5 is added to the third quartile of the price to determine an upper limit.
 * The value: IQR * 1.5 is subtracted from the third quartile and set as the lower limit. All values outside this range
 * are considered outliers.
 */
public class IQROutlierDetectionStrategy implements IOutlierDetectionStrategy {
    /**
     * getMidpointOfTwoDataPoints returns the price quartile value between two indices, an average of price between
     * the two indices are calculated. If both indices must be any x such that 0 <= x <= dataPoints.size() - 1
     * @param dataPoints list of data points
     * @param lower lower index
     * @param upper upper index
     * @return price quartile value
     */
    private double getPriceAvgOfTwoDataPoints(List<DataPoint> dataPoints, int lower, int upper) {
        return (dataPoints.get(lower).price + dataPoints.get(upper).price) / 2;
    }

    /**
     * getNthPriceOfDataPoints returns the price of the data point at position n, index n must be an x such that
     * 0 <= x <= dataPoints.size() - 1
     * @param dataPoints list of data points
     * @param n index of dataPoint
     * @return price value of data point at position n
     */
    private double getNthPriceOfDataPoints(List<DataPoint> dataPoints, int n) {
        return dataPoints.get(n).price;
    }

    /**
     * getPriceQuartileValue returns the price value at the specified quartile index
     * @param dataPoints list of data points
     * @param quartileIndex index value determined from either the formula for calculation of first quartile or third
     *                      quartile
     * @return price value at index
     */
    private double getPriceQuartileValue(List<DataPoint> dataPoints, double quartileIndex) {
        double priceQuartileValue;
        if (quartileIndex % 1 == 0) {
            priceQuartileValue = getNthPriceOfDataPoints(dataPoints, (int)quartileIndex - 1);
        } else {
            int lower = (int) Math.floor(quartileIndex);
            int upper = (int) Math.ceil(quartileIndex);
            priceQuartileValue = getPriceAvgOfTwoDataPoints(dataPoints, lower - 1, upper -1);
        }
        return priceQuartileValue;
    }

    /**
     * Implementation of {@link IOutlierDetectionStrategy}.getOutliers(), sorts the data points by price, determines
     * IQR and returns values that don't fall within Q1 - (IQR * 1.5) and Q3 + (IQR * 1.5)
     * @param dataPoints list of data points
     * @return values that don't fall within Q1 - (IQR * 1.5) and Q3 + (IQR * 1.5)
     */
    @Override
    public List<DataPoint> getOutliers(List<DataPoint> dataPoints) {
        List<DataPoint> outliers;

        if (dataPoints.size() <= 4) {
            return new LinkedList<>();
        }

        dataPoints.sort(new Comparator<>() {
            @Override
            public int compare(DataPoint o1, DataPoint o2) {
                if (o1.price > o2.price) {
                    return 1;
                } else if (o1.price < o2.price) {
                    return -1;
                }
                return 0;
            }
        });
        double firstQuartileIndex = (double)(dataPoints.size() + 1) / 4;
        double thirdQuartileIndex = 3 * (double)(dataPoints.size() + 1) / 4;

        double firstPriceQuartileValue = getPriceQuartileValue(dataPoints, firstQuartileIndex);
        double thirdPriceQuartileValue = getPriceQuartileValue(dataPoints, thirdQuartileIndex);

        double k = (thirdPriceQuartileValue - firstPriceQuartileValue) * 1.5;
        double lowerLimit = firstPriceQuartileValue - k;
        double upperLimit = thirdPriceQuartileValue + k;

        outliers = dataPoints.stream().filter(dataPoint -> dataPoint.price < lowerLimit || dataPoint.price > upperLimit).collect(Collectors.toList());
        return outliers;
    }

    /**
     * Implementation of {@link IOutlierDetectionStrategy}.removeOutliers(), removes outliers from list of data points
     * @param dataPoints list of data points
     * @param outliers list of outlier data points
     * @return list of data points without outliers
     */
    @Override
    public List<DataPoint> removeOutliers(List<DataPoint> dataPoints, List<DataPoint> outliers) {
        HashMap<LocalDate, DataPoint> outlierMap = new HashMap<>();
        outliers.forEach(dataPoint -> {
            outlierMap.put(dataPoint.date, dataPoint);
        });

        return dataPoints.stream().filter(dataPoint -> !outlierMap.containsKey(dataPoint.date)).collect(Collectors.toList());
    }
}
