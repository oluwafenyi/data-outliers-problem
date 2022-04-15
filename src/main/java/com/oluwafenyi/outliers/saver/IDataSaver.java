package com.oluwafenyi.outliers.saver;

import com.oluwafenyi.outliers.DataPoint;

import java.util.List;

/**
 * Data saver interface that defines methods for storing a list of data points
 */
public interface IDataSaver {
    /**
     * save function
     * @param dataPoints list of data points
     * @throws DataSavingException on error saving
     */
    void save(List<DataPoint> dataPoints) throws DataSavingException;
}
