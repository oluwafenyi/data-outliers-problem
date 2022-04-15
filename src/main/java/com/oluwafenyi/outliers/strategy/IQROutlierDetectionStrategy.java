package com.oluwafenyi.outliers.strategy;

import com.oluwafenyi.outliers.DataPoint;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IQROutlierDetectionStrategy implements IOutlierDetectionStrategy {
    private double getPriceQuartile(List<DataPoint> dataPoints, double index) {
        if (index % 1 == 0) {
            return dataPoints.get((int) index).price;
        }

        int lower = (int) Math.floor(index);
        int upper = (int) Math.ceil(index);

        return (dataPoints.get(lower).price + dataPoints.get(upper).price) / 2;
    }

    @Override
    public List<DataPoint> getOutliers(List<DataPoint> historicalDataPoints, List<DataPoint> dataPoints) {
        List<DataPoint> outliers;
        List<DataPoint> combinedList = Stream.concat(historicalDataPoints.stream(), dataPoints.stream()).collect(Collectors.toList());

        if (combinedList.size() < 4) {
            return new LinkedList<>();
        }

        combinedList.sort(new Comparator<>() {
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
        double firstQuartileIndex = (double)(combinedList.size() + 1) / 4;
        double thirdQuartileIndex = 3 * (double)(combinedList.size() + 1) / 4;

        double firstPriceQuartile = getPriceQuartile(combinedList, firstQuartileIndex);
        double thirdPriceQuartile = getPriceQuartile(combinedList, thirdQuartileIndex);

        double k = (thirdPriceQuartile - firstPriceQuartile) * 1.5;
        double lowerLimit = firstPriceQuartile - k;
        double upperLimit = thirdPriceQuartile + k;

        outliers = dataPoints.stream().filter(dataPoint -> dataPoint.price < lowerLimit || dataPoint.price > upperLimit).collect(Collectors.toList());
        return outliers;
    }

    @Override
    public List<DataPoint> removeOutliers(List<DataPoint> dataPoints, List<DataPoint> outliers) {
        HashMap<String, DataPoint> outlierMap = new HashMap<>();
        outliers.forEach(dataPoint -> {
            outlierMap.put(dataPoint.date, dataPoint);
        });

        return dataPoints.stream().filter(dataPoint -> !outlierMap.containsKey(dataPoint.date)).collect(Collectors.toList());
    }
}
