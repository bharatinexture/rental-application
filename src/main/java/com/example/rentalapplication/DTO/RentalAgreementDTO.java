package com.example.rentalapplication.DTO;

import lombok.Data;

@Data
public class RentalAgreementDTO {

    private String rentalAgreementNumber;
    private String toolCode;
    private String toolType;
    private String brand;
    private String rentalDays;
    private String checkOutDate;
    private String dueDate;
    private String dailyRentalCharge;
    private String chargedDays;
    private String preDiscountCharge;
    private String discountPercent;
    private String discountAmount;
    private String finalCharge;
}
