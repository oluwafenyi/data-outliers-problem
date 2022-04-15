package com.oluwafenyi.outliers.saver;

/**
 * Custom Exception for error during {@link IDataSaver}.save()
 */
public class DataSavingException extends Exception {
    /**
     * for throwing an instance of {@link DataSavingException}
     * @param errorMessage error message
     */
    public DataSavingException(String errorMessage) {
        super(errorMessage);
    }
}
