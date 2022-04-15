package com.oluwafenyi.outliers.csvhandlers;

import com.oluwafenyi.outliers.DataPoint;
import com.oluwafenyi.outliers.loader.DataLoadingException;
import com.oluwafenyi.outliers.loader.IDataLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A concrete of the {@link IDataLoader} interface that supports loading data points from CSV files.
 * CSV files are expected to have two columns, first for date and second for price. The lines should be delimited by
 * commas, and should not contain quotation wrapping.
 */
public class CSVReader implements IDataLoader {
    /**
     * path for input CSV file
     */
    protected Path filePath;
    /**
     * Flag to set if headers are present in the CSV file
     */
    private final boolean headersSet;

    /**
     * Constructor for creating an instance of {@link CSVReader}
     * @param filePath absolute path for input CSV file
     * @param headersSet flag for if headers are present the CSV file
     */
    public CSVReader(String filePath, boolean headersSet) {
        this.filePath = Paths.get(filePath);
        this.headersSet = headersSet;
    }

    /**
     * Implementation for {@link IDataLoader}.load(), iterates over each line in CSV file, and splits line by ",",
     * picking the date from the first column and the price from the second column.
     * @return list of data points present in the CSV file
     * @throws DataLoadingException if file does not exist or there was an error reading the file
     */
    @Override
    public List<DataPoint> load() throws DataLoadingException {
        List<DataPoint> data = new LinkedList<>();
        Scanner scanner;

        if (!Files.exists(this.filePath)) {
            throw new DataLoadingException("File does not exist");
        }

        try {
            scanner = new Scanner(this.filePath);
        } catch (IOException ex) {
            throw new DataLoadingException(ex.getMessage());
        }

        if (headersSet) {
            scanner.next();
        }
        while (scanner.hasNext()) {
            String lineStr = scanner.next().strip();
            if (lineStr.equals("")) {
                continue;
            }
            String[] line = lineStr.split(",");
            String date = line[0];
            String price = line[1];
            data.add(new DataPoint(date, Double.parseDouble(price)));
        }
        scanner.close();
        return data;
    }
}

