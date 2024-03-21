package com.example.rentalapplication.service;

import com.example.rentalapplication.DTO.CheckoutRequest;
import com.example.rentalapplication.DTO.RentalAgreement;
import com.example.rentalapplication.entity.Checkout;
import com.example.rentalapplication.entity.Tool;
import com.example.rentalapplication.entity.ToolTypes;
import com.example.rentalapplication.repository.CheckoutRepository;
import com.example.rentalapplication.repository.ToolRepository;
import com.example.rentalapplication.repository.ToolTypesRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CheckoutService {

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ToolTypesRepository toolTypesRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    public RentalAgreement processCheckOut(CheckoutRequest checkoutRequest)  {
        if (checkoutRequest.getRentalDayCount() < 1) {
            throw new ValidationException("Rental day count must be 1 or greater.");
        }
        if (checkoutRequest.getDiscountPercent() < 0 || checkoutRequest.getDiscountPercent() > 100) {
            throw new ValidationException("Discount percent must be between 0 and 100.");
        }

        Tool tool = Optional.ofNullable(toolRepository.findToolByToolCode(checkoutRequest.getToolCode()))
                .orElseThrow(() -> new IllegalArgumentException("Tool code not found."));

        ToolTypes toolType = Optional.ofNullable(toolTypesRepository.findByType(tool.getToolType()))
                .orElseThrow(() -> new IllegalArgumentException("Tool type not found."));

        RentalAgreement rentalAgreement = new RentalAgreement(checkoutRequest, tool, toolType);
        rentalAgreement.displayRentalAgreement();

        return rentalAgreement;

    }



}


