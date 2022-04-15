package com.oluwafenyi.outliers;

/**
 * Represents a single DataPoint entry
 */
public class DataPoint {
    /**
     * Date value for where data point was recorded
     */
    public final String date;
    /**
     * Price value observed at particular point
     */
    public final double price;

    /**
     * Constructor for creation of new Datapoint Objects
     * @param date date of recorded data point
     * @param price price value observed
     */
    public DataPoint(String date, double price) {
        this.date = date;
        this.price = price;
    }
}
