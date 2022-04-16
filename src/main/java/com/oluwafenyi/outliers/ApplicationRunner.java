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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Class containing an interactive program for retrieving outliers from csv datasets
 */
public class ApplicationRunner {
    /**
     * Scanner instance to read input from user
     */
    private static final Scanner input = new Scanner(System.in);

    /**
     * getValidFilePath takes user input and returns a path for an existing file entered by the user, if the user
     * supplies an invalid path, they are prompted to try again
     * @return filePath
     */
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

    /**
     * getValidSavePath takes user input and returns a valid path where a file does not already exist, if user supplies
     * an invalid path, they are prompted to try again
     * @return filePath
     */
    public static String getValidSavePath() {
        while (true) {
            System.out.println("Enter path to save output CSV file:");
            String filePath = input.next();
            Path path = Paths.get(filePath);
            Path directory = path.getParent();
            if (Files.exists(path)) {
                System.out.println("error: a file exists at that path, please choose another");
            } else {
                if (Files.isDirectory(directory)) {
                    return filePath;
                }
                System.out.println("error: invalid directory path");
            }
        }
    }

    /**
     * getDatePoint gets a date input from the user, if an invalid date value is supplied, the user is prompted to try
     * again
     * @return LocalDate instance
     */
    public static LocalDate getDatePoint() {
        while (true) {
            System.out.println("Enter Date Checkpoint for Outlier determination (format:dd-MM-yyyy):");
            String date = input.next();
            LocalDate dateObj;
            try {
                dateObj = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                return dateObj;
            } catch (Exception ex) {
                System.out.println("Error parsing date: " + ex.getMessage());
            }
        }
    }

    /**
     * Interactive method for detecting outliers, on each run of the detection algorithm, the data points considered
     * are stored for the detection of outliers on further runs.
     */
    public static void run() {
        IOutlierDetectionStrategy strategy = new IQROutlierDetectionStrategy();
        System.out.println("Ensure your input CSV file contains headers.");
        OutlierDetector detector = new OutlierDetector(strategy);
        String inputFilePath = getValidFilePath();
        String outputFilePath = getValidSavePath();

        IDataLoader loader = new CSVReader(inputFilePath, true);
        List<DataPoint> data = null;
        try {
            data = loader.load();
        } catch (DataLoadingException ex) {
            System.out.println("error: could not load data from the CSV file supplied. " + ex);
            return;
        }
        detector.addDataPoints(data);

        LocalDate date = getDatePoint();
        List<DataPoint> outliers = detector.getOutliersUpToDate(date);

        if (outliers.size() > 0) {
            System.out.println("\nThe following outliers were found in your data set:");
            outliers.forEach(dataPoint -> {
                System.out.println(dataPoint.date + ": " + dataPoint.price);
            });
            System.out.println("Removing them from data set...");
        }

        List<DataPoint> cleanedData = detector.removeOutliersUptoDate(outliers, date);
        IDataSaver saver = new CSVWriter(outputFilePath);
        try {
            saver.save(cleanedData);
            System.out.println("\nOutput file written to " + outputFilePath);
        } catch (DataSavingException ex) {
            System.out.println("error: could not save data to the path. " + ex);
        }
    }

    /**
     * calls the run() method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        run();
    }
}