package com.example.rentalapplication.service;

import com.example.rentalapplication.dto.CheckoutRequest;
import com.example.rentalapplication.model.RentalAgreement;
import com.example.rentalapplication.model.Tool;
import com.example.rentalapplication.model.ToolTypes;
import com.example.rentalapplication.util.DBUtils;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutService {

    /**
     * Processes a checkout request by validating it and creating a Rental Agreement.
     * It ensures the rental day count is valid and the discount percent is within the acceptable range.
     * The method retrieves tool information from the database-util and calculates the rental charges
     * based on the tool type and the checkout request details.
     *
     * @param checkoutRequest The request containing the details for the tool rental checkout.
     * @return RentalAgreement A detailed agreement containing the terms of the rental.
     * @throws ValidationException if the rental day count is less than 1 or the discount percent is not within 0-100.
     */
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


        Tool tool = DBUtils.tools.get(checkoutRequest.getToolCode());

        ToolTypes toolType = DBUtils.toolTypes.get(tool.getToolType());

        // all the calculations regarding the charged days, discount, and holidays happen at the very moment an instance of RentalAgreement is created
        RentalAgreement rentalAgreement = new RentalAgreement(checkoutRequest, tool, toolType);
        rentalAgreement.displayRentalAgreement();


        return rentalAgreement;

    }

}