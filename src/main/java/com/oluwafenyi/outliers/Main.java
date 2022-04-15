package com.oluwafenyi.outliers;

import com.oluwafenyi.outliers.csvhandlers.CSVReader;
import com.oluwafenyi.outliers.csvhandlers.CSVWriter;
import com.oluwafenyi.outliers.loader.DataLoadingException;
import com.oluwafenyi.outliers.loader.IDataLoader;
import com.oluwafenyi.outliers.saver.DataSavingException;
import com.oluwafenyi.outliers.saver.IDataSaver;
import com.oluwafenyi.outliers.strategy.IOutlierDetectionStrategy;
import com.oluwafenyi.outliers.strategy.IQROutlierDetectionStrategy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);


    public static String getValidFilePath() {
        while (true) {
            System.out.println("Enter path to input CSV file:");
            String filePath = input.next();
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                System.out.println("error: could not find file at that path.");
            } else {
                return filePath;
            }
        }
    }

    public static String getValidSavePath() {
        while (true) {
            System.out.println("Enter path to save output CSV file:");
            String filePath = input.next();
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                System.out.println("error: a file exists at that path, please choose another");
            } else {
                return filePath;
            }
        }
    }

    public static void main(String[] args) {
        IOutlierDetectionStrategy strategy = new IQROutlierDetectionStrategy();
        OutlierDetector detector = new OutlierDetector(strategy);

        System.out.println("Ensure your input CSV file contains headers.");
        while (true) {
            String inputFilePath = getValidFilePath();
            String outputFilePath = getValidSavePath();

            IDataLoader loader = new CSVReader(inputFilePath, true);
            List<DataPoint> data = null;
            try {
                data = loader.load();
            } catch (DataLoadingException ex) {
                System.out.println("error: could not load data from the CSV file supplied. " + ex);
                continue;
            }
            List<DataPoint> outliers = detector.getOutliers(data);

            if (outliers.size() > 0) {
                System.out.println("\nThe following outliers were found in your data set:");
                outliers.forEach(dataPoint -> {
                    System.out.println(dataPoint.date + ": " + dataPoint.price);
                });
            }

            List<DataPoint> cleanedData = detector.removeOutliers(data, outliers);
            IDataSaver saver = new CSVWriter(outputFilePath);
            try {
                saver.save(cleanedData);
                System.out.println("\nOutput file written to " + outputFilePath);
            } catch (DataSavingException ex) {
                System.out.println("error: could not save data to the path. " + ex);
            }
        }
    }
}