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

public class CSVWriter implements IDataSaver {
    protected Path filePath;
    protected List<String> headers;

    public CSVWriter(String filePath, List<String> headers) {
        this.filePath = Paths.get(filePath);
        this.headers = headers;
    }
    public CSVWriter(String filePath) {
        this.filePath = Paths.get(filePath);
        this.headers = new LinkedList<>(Arrays.asList("date", "price"));
    }

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
