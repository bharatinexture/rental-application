package com.example.rentalapplication.model;

import com.example.rentalapplication.dto.CheckoutRequest;
import com.example.rentalapplication.util.DateUtil;
import com.example.rentalapplication.util.NumberFormatUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

/**
 * Represents a rental agreement detailing the terms of the tool rental.
 * Includes identification information, rental terms, charges, due date, and the discount applied.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalAgreement {

    private Long rentalAgreementNumber;
    private String toolCode;
    private String toolType;
    private String brand;
    private int rentalDays;
    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private BigDecimal dailyRentalCharge;
    private int chargedDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;


    /**
     * Constructor that creates a Rental Agreement based on the checkout request, tool, and tool type.
     * It calculates the due date, chargeable days, pre-discount charge, discount amount, and final charge.
     *
     * @param checkout The checkout request containing rental start date, number of rental days, and discount percentage.
     * @param tool     The tool being rented.
     * @param toolType The type of the tool, which determines rental charge and chargeable days policy.
     */
    public RentalAgreement(CheckoutRequest checkout, Tool tool, ToolTypes toolType) {
        this.toolCode = tool.getToolCode();
        this.toolType = tool.getToolType();
        this.brand = tool.getBrand();
        this.rentalDays = checkout.getRentalDayCount();
        this.checkOutDate = DateUtil.convertStringToLocalDate(checkout.getCheckOutDate());
        this.dueDate = calculateDueDate(checkOutDate, checkout.getRentalDayCount());
        this.dailyRentalCharge = toolType.getDailyRentalCharge();
        this.chargedDays = countChargeDays(checkOutDate, dueDate, toolType);
        this.preDiscountCharge = dailyRentalCharge.multiply(BigDecimal.valueOf(chargedDays)).setScale(2, RoundingMode.HALF_UP);
        this.discountPercent = checkout.getDiscountPercent();
        this.discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent / 100.0)).setScale(2, RoundingMode.HALF_UP);
        this.finalCharge = preDiscountCharge.subtract(discountAmount);
    }

    /**
     * Calculates the due date using the available util methods
     */
    private LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDays) {
        return  checkoutDate.plusDays(rentalDays);
    }

    /**
     * Counts the total chargedDays given the checkoutDate, dueDate and the tool's type
     */
    private int countChargeDays(LocalDate checkoutDate, LocalDate dueDate, ToolTypes toolType) {
        int chargeDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);
        while (currentDate.isBefore(dueDate) || currentDate.isEqual(dueDate)) {
            if (isChargeDay(currentDate, toolType)) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return chargeDays;
    }

    /**
     * Checks whether a certain day is a charge day, charging the customer for the rented tool on said day
     */
    private boolean isChargeDay(LocalDate date, ToolTypes toolType) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return toolType.getWeekendCharge();
        } else if (isHoliday(date)) {
            return toolType.getHolidayCharge();
        } else {
            return toolType.getWeekdayCharge();
        }
    }

    /**
     * Checks whether a certain date is a Holiday.
     * For now, only two holidays: The Independence Day (4th of July),
     * and Labor Day (First Monday of September).
     */
    private boolean isHoliday(LocalDate date) {
        Month month = date.getMonth();
        int day = date.getDayOfMonth();

        if (month == Month.JULY) {
            // Handle Independence day
            return day == 4
                    || (day == 3 && date.getDayOfWeek() == DayOfWeek.FRIDAY)
                    || (day == 5 && date.getDayOfWeek() == DayOfWeek.MONDAY);


        } else
            // Handle Labor day
            return month == Month.SEPTEMBER && day<=7 && date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    /**
     * Displays the details of the rental agreement on the console.
     * The output includes formatted tool-information, rental terms, and calculated charges.
     */
    public void displayRentalAgreement() {
        System.out.println("-----------------------------------Rental Agreement-----------------------------------------");
        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + brand);
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Checkout date: "+ DateUtil.formatDate(checkOutDate));
        System.out.println("Due date: "+ DateUtil.formatDate(dueDate));
        System.out.println("Daily rental charge: "+ NumberFormatUtil.formatAmount(dailyRentalCharge));
        System.out.println("Charge days: " + chargedDays);
        System.out.println("Pre-discount charge: " + NumberFormatUtil.formatAmount(preDiscountCharge));
        System.out.println("Discount percent: " + discountPercent + "%");
        System.out.println("Discount amount: " + NumberFormatUtil.formatAmount(discountAmount));
        System.out.println("Final charge: " + NumberFormatUtil.formatAmount(finalCharge));
        System.out.println("----------------------------------------------------------------------------");
    }

}