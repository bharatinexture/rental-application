package com.example.rentalapplication.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class providing methods for date operations in the context of the rental application.
 * It provides functionality to format and parse dates according to the application's standard date format.
 */
public class DateUtil {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

    /**
     * Formats a given LocalDate(yyyy-MM-dd) into a string representation following the format 'MM/dd/yy'.
     *
     * @param date The LocalDate instance to be formatted.
     * @return A string representation of the given date.
     */
    public static String formatDate(LocalDate date){
        return dateFormatter.format(date);
    }

    /**
     * Parses a string representing a date in the format 'MM/dd/yy' to a LocalDate(yyyy-MM-dd) instance.
     *
     * @param date A string representation of a date that matches the format 'MM/dd/yy'.
     * @return The LocalDate instance representing the given date string.
     */
    public static LocalDate convertStringToLocalDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }
}