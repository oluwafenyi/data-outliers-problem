package com.oluwafenyi.outliers.loader;

import com.oluwafenyi.outliers.DataPoint;

import java.util.List;

public interface IDataLoader{
    List<DataPoint> load() throws DataLoadingException;
}
