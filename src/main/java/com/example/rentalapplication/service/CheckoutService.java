package com.example.rentalapplication.service;

import com.example.rentalapplication.DTO.CheckoutRequest;
import com.example.rentalapplication.entity.RentalAgreement;
import com.example.rentalapplication.entity.Tool;
import com.example.rentalapplication.entity.ToolTypes;
import com.example.rentalapplication.exception.ToolNotFoundException;
import com.example.rentalapplication.repository.RentalAgreementRepository;
import com.example.rentalapplication.repository.ToolRepository;
import com.example.rentalapplication.repository.ToolTypesRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutService {

    private final ToolRepository toolRepository;

    private final ToolTypesRepository toolTypesRepository;

    private final RentalAgreementRepository rentalAgreementRepository;


    public RentalAgreement processCheckOut(CheckoutRequest checkoutRequest)  {
        log.info("Processing checkout request: {}", checkoutRequest);

        if (checkoutRequest.getRentalDayCount() < 1) {
            log.error("Validation failed: Rental day count must be 1 or greater.");
            throw new ValidationException("Rental day count must be 1 or greater.");
        }
        if (checkoutRequest.getDiscountPercent() < 0 || checkoutRequest.getDiscountPercent() > 100) {
            log.error("Validation failed: Discount percent must be between 0 and 100.");
            throw new ValidationException("Discount percent must be between 0 and 100.");
        }

        Tool tool = Optional.ofNullable(toolRepository.findToolByToolCode(checkoutRequest.getToolCode()))
                .orElseThrow(() -> {
                    log.error("Tool code not found: {}", checkoutRequest.getToolCode());
                    return new ToolNotFoundException("Tool code not found.");
                });
        log.info("Tool was found: " + tool);

        ToolTypes toolType = Optional.ofNullable(toolTypesRepository.findByType(tool.getToolType()))
                .orElseThrow(() -> new ToolNotFoundException("Tool type not found."));
        log.info("ToolType was found: " + toolType);

        // all the calculations regarding the charged days, discount, and holidays happen at the very moment an instance of RentalAgreement is created
        RentalAgreement rentalAgreement = new RentalAgreement(checkoutRequest, tool, toolType);
        rentalAgreement.displayRentalAgreement();

        rentalAgreementRepository.save(rentalAgreement);
        log.info("RentalAgreement was saved into the db successfully.");

        return rentalAgreement;

    }

}