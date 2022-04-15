package com.oluwafenyi.outliers.csvhandlers;

import com.oluwafenyi.outliers.DataPoint;
import com.oluwafenyi.outliers.saver.DataSavingException;
import com.oluwafenyi.outliers.saver.IDataSaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A concrete implementation of the {@link IDataSaver} interface that supports saving data points to CSV files.
 */
public class CSVWriter implements IDataSaver {
    /**
     * Path for output CSV file
     */
    protected Path filePath;
    /**
     * Headers to write to file
     */
    protected List<String> headers;

    /**
     * Constructor to create an instance of {@link CSVWriter} with custom headers
     * @param filePath destination path for output file
     * @param headers a row of headers to prepend to the CSV file, non-null
     */
    public CSVWriter(String filePath, List<String> headers) {
        this.filePath = Paths.get(filePath);
        this.headers = headers.subList(0, 2);
    }

    /**
     * Constructor to create an instance of {@link CSVWriter} with default headers,
     * default headers are "date" and "price"
     * @param filePath destination path for output file
     */
    public CSVWriter(String filePath) {
        this.filePath = Paths.get(filePath);
        this.headers = new LinkedList<>(Arrays.asList("date", "price"));
    }

    /**
     * Implementation for {@link IDataSaver}.save(), dumps list of data points to a csv format
     * where the date is the first row and the price is the second row of the csv.
     * @param dataPoints list of data points
     * @throws DataSavingException if there was an error writing file or creating at the specified path
     */
    @Override
    public void save(List<DataPoint> dataPoints) throws DataSavingException {
        File output = new File(this.filePath.toString());
        try (PrintWriter pw = new PrintWriter(output)){
            pw.write(String.join(",", headers) + "\n");
            dataPoints.forEach(dataPoint -> {
                pw.write(dataPoint.date + "," + dataPoint.price + "\n");
            });
        } catch (FileNotFoundException ex) {
            throw new DataSavingException(ex.getMessage());
        }
    }
}
