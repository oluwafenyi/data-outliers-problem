package com.oluwafenyi.outliers.loader;

import com.oluwafenyi.outliers.DataPoint;

import java.util.List;

/**
 * Data loader interface that defines the methods for sourcing a list of data points.
 */
public interface IDataLoader{
    /**
     * load function
     * @return list of data points
     * @throws DataLoadingException on error loading
     */
    List<DataPoint> load() throws DataLoadingException;
}
