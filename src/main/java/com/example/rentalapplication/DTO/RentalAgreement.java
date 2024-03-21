package com.example.rentalapplication.DTO;

import com.example.rentalapplication.entity.Tool;
import com.example.rentalapplication.entity.ToolTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalAgreement {

    private String toolCode;;
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

    public RentalAgreement(CheckoutRequest checkout, Tool tool, ToolTypes toolType) {
        this.toolCode = tool.getToolCode();
        this.toolType = tool.getToolType();
        this.brand = tool.getBrand();
        this.rentalDays = checkout.getRentalDayCount();
        this.checkOutDate = checkout.getCheckOutDate();
        this.dueDate = calculateDueDate(checkout.getCheckOutDate(), checkout.getRentalDayCount());
        this.dailyRentalCharge = toolType.getDailyRentalCharge();
        this.chargedDays = countChargeDays(checkout.getCheckOutDate(), dueDate, toolType);
        this.preDiscountCharge = dailyRentalCharge.multiply(BigDecimal.valueOf(chargedDays)).setScale(2, RoundingMode.HALF_UP);
        this.discountPercent = checkout.getDiscountPercent();
        this.discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent / 100.0)).setScale(2, RoundingMode.HALF_UP);
        this.finalCharge = preDiscountCharge.subtract(discountAmount);
    }

    private LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDays) {
        return  checkoutDate.plusDays(rentalDays);
    }

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

    private boolean isHoliday(LocalDate date) {
        Month month = date.getMonth();
        int day = date.getDayOfMonth();

        if (month == Month.JULY) {
            // Handle Independence day
            return day == 4
                    || (day == 3 && date.getDayOfWeek() == DayOfWeek.FRIDAY)
                    || (day == 5 && date.getDayOfWeek() == DayOfWeek.MONDAY);


        } else
            return month == Month.SEPTEMBER && day<=7 && date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    public void displayRentalAgreement() {
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + brand);
        System.out.println("Rental days: " + rentalDays);
        System.out.printf("Checkout date: %tm/%td/%ty%n", checkOutDate, checkOutDate, checkOutDate);
        System.out.printf("Due date: %tm/%td/%ty%n", dueDate, dueDate, dueDate);
        System.out.printf("Daily rental charge: $%.2f%n", dailyRentalCharge);
        System.out.println("Charge days: " + chargedDays);
        System.out.printf("Pre-discount charge: $%.2f%n", preDiscountCharge);
        System.out.println("Discount percent: " + discountPercent + "%");
        System.out.printf("Discount amount: $%.2f%n", discountAmount);
        System.out.printf("Final charge: $%.2f%n", finalCharge);
        System.out.println("----------------------------------------------------------------------------");
    }
}


