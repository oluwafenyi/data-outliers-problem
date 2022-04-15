package com.oluwafenyi.outliers.loader;

/**
 * Custom Exception for error during {@link IDataLoader}.load()
 */
public class DataLoadingException extends Exception {
    /**
     * for throwing an instance of {@link IDataLoader}
     * @param errorMessage error message
     */
    public DataLoadingException(String errorMessage) {
        super(errorMessage);
    }
}
