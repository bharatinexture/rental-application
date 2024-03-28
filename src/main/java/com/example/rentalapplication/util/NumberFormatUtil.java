package com.example.rentalapplication.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class for formatting numbers as currency strings.
 * This class is used throughout the rental application to ensure consistent
 * currency formatting for display or printing.
 */
public class NumberFormatUtil {
    private static final NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    /**
     * Formats a BigDecimal value as a currency string according to the US Locale.
     * For example, an input of new BigDecimal("10.00") will be formatted as "$10.00",
     * and an input of new BigDecimal("10000.00") will be formatted as "$10,000.00".
     *
     * @param amount The BigDecimal value to be formatted.
     * @return A string representing the formatted currency value.
     */
    public static String formatAmount(BigDecimal amount){
        return numberFormatter.format(amount);
    }
}
