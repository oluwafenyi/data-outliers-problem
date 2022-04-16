package com.oluwafenyi.outliers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single DataPoint entry
 */
public class DataPoint {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    /**
     * Date value for where data point was recorded
     */
    public final LocalDate date;
    /**
     * Price value observed at particular point
     */
    public final double price;

    /**
     * Constructor for creation of new Datapoint Objects
     * @param date date of recorded data point
     * @param price price value observed
     */
    public DataPoint(LocalDate date, double price) {
        this.date = date;
        this.price = price;
    }

    /**
     * Constructor for creation of new Datapoint Objects, date value is expected to have the format: dd-MM-yyyy
     * @param date date of recorded data point in format dd-MM-yyyy
     * @param price price value observed
     */
    public DataPoint(String date, double price) {
        this.date = LocalDate.parse(date, dateTimeFormatter);
        this.price = price;
    }
}
