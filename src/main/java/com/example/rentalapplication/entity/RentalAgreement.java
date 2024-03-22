package com.example.rentalapplication.entity;

import com.example.rentalapplication.DTO.CheckoutRequest;
import jakarta.persistence.*;
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

@Data
@Entity
@Table(name = "rental_agreement")
@AllArgsConstructor
@NoArgsConstructor
public class RentalAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "rental_agreement_number")
    private Long rentalAgreementNumber;
    @Column(nullable = false, name = "tool_code")
    private String toolCode;
    @Column(nullable = false, name = "tool_type")
    private String toolType;
    @Column(nullable = false, name = "brand")
    private String brand;
    @Column(nullable = false, name = "rental_days")
    private int rentalDays;
    @Column(nullable = false, name = "checkout_date")
    private LocalDate checkOutDate;
    @Column(nullable = false, name = "due_date")
    private LocalDate dueDate;
    @Column(nullable = false, name = "daily_rental_charge")
    private BigDecimal dailyRentalCharge;
    @Column(nullable = false, name = "charge_days")
    private int chargedDays;
    @Column(nullable = false, name = "pre_discount_charge")
    private BigDecimal preDiscountCharge;
    @Column(nullable = false, name = "discount_percent")
    private int discountPercent;
    @Column(nullable = false, name = "discount_amount")
    private BigDecimal discountAmount;
    @Column(nullable = false, name = "final_charge")
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
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + brand);
        System.out.println("Rental days: " + rentalDays);
        System.out.printf("Checkout date: %tm/%td/%ty%n", checkOutDate, checkOutDate, checkOutDate);
        System.out.printf("Due date: %tm/%td/%ty%n", dueDate, dueDate, dueDate);
        System.out.printf("Daily rental charge: %s%n", currencyFormatter.format(dailyRentalCharge));
        System.out.println("Charge days: " + chargedDays);
        System.out.println("Pre-discount charge: " + currencyFormatter.format(preDiscountCharge));
        System.out.println("Discount percent: " + discountPercent + "%");
        System.out.println("Discount amount: " + currencyFormatter.format(discountAmount));
        System.out.println("Final charge: " + currencyFormatter.format(finalCharge));
        System.out.println("----------------------------------------------------------------------------");
    }

}