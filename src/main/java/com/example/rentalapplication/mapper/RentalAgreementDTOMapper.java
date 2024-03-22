package com.example.rentalapplication.mapper;


import com.example.rentalapplication.DTO.RentalAgreementDTO;

import com.example.rentalapplication.entity.RentalAgreement;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class RentalAgreementDTOMapper {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    public static RentalAgreementDTO entityToDto(RentalAgreement entity) {
        RentalAgreementDTO dto = new RentalAgreementDTO();
        dto.setRentalAgreementNumber(String.valueOf(entity.getRentalAgreementNumber()));
        dto.setToolCode(entity.getToolCode());
        dto.setToolType(entity.getToolType());
        dto.setBrand(entity.getBrand());
        dto.setRentalDays(String.valueOf(entity.getRentalDays()));
        dto.setCheckOutDate(entity.getCheckOutDate().format(dateFormatter));
        dto.setDueDate(entity.getDueDate().format(dateFormatter));
        dto.setDailyRentalCharge(currencyFormatter.format(entity.getDailyRentalCharge()));
        dto.setChargedDays(String.valueOf(entity.getChargedDays()));
        dto.setPreDiscountCharge(currencyFormatter.format(entity.getPreDiscountCharge()));
        dto.setDiscountPercent(String.valueOf(entity.getDiscountPercent()) + "%");
        dto.setDiscountAmount(currencyFormatter.format(entity.getDiscountAmount()));
        dto.setFinalCharge(currencyFormatter.format(entity.getFinalCharge()));
        return dto;
    }
}
