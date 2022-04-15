package com.oluwafenyi.outliers.saver;

import com.oluwafenyi.outliers.DataPoint;

import java.util.List;

public interface IDataSaver {
    void save(List<DataPoint> dataPoints) throws DataSavingException;
}
