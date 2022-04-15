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

public class CSVReader implements IDataLoader {
    protected Path filePath;
    private final boolean headersSet;

    public CSVReader(String filePath, boolean headersSet) {
        this.filePath = Paths.get(filePath);
        this.headersSet = headersSet;
    }

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
            String[] line = scanner.next().split(",");
            String date = line[0];
            String price = line[1];
            data.add(new DataPoint(date, Double.parseDouble(price)));
        }
        scanner.close();
        return data;
    }
}

