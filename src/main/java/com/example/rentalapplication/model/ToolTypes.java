package com.example.rentalapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents the characteristics of a type of tool available for rental.
 * It encapsulates the rental charge policy and the availability for charge on weekdays,
 * weekends, and holidays.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolTypes {
    /**
     * The name or identifier of the tool type. It describes the category of tool, such as "Ladder", "Chainsaw", etc.
     */
    private String type;
    /**
     * The daily rental charge rate for this tool type. This value is used to calculate the cost of the rental.
     */
    private BigDecimal dailyRentalCharge;
    /**
     * Indicator of whether this tool type incurs a charge on weekdays.
     */
    private Boolean weekdayCharge;
    /**
     * Indicator of whether this tool type incurs a charge on weekends.
     */
    private Boolean weekendCharge;
    /**
     * Indicator of whether this tool type incurs a charge on holidays.
     */
    private Boolean holidayCharge;
}