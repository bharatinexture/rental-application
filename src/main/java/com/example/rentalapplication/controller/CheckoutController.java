package com.example.rentalapplication.controller;

import com.example.rentalapplication.DTO.CheckoutRequest;
import com.example.rentalapplication.entity.RentalAgreement;
import com.example.rentalapplication.exception.ToolNotFoundException;
import com.example.rentalapplication.mapper.RentalAgreementDTOMapper;
import com.example.rentalapplication.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental")
@Slf4j
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private RentalAgreementDTOMapper rentalAgreementDTOMapper;

    /**
     * Processes a checkout request and generates a rental agreement.
     * This endpoint receives and validates the checkout request, ensures the tool and its type are available,
     * and calculates the rental charges, discount, and final charge before creating a rental agreement.
     *
     * Validation includes checking that the rental day count is at least 1 and the discount percentage
     * is within the range of 0 to 100. If the tool code provided in the request does not correspond to an
     * existing tool or the tool's type is not found, a {@link ToolNotFoundException} is thrown.
     *
     * @param checkoutRequest the checkout request to be processed, containing details such as the tool code,
     * rental day count, discount percent, and the rental start date
     * @return a {@link ResponseEntity} containing either the rental agreement in case of success or an error message in case of failure
     */
    @PostMapping("/checkout")
    public ResponseEntity<Object> processCheckout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        log.info("Processing checkout for request: {}", checkoutRequest);
        try {
            RentalAgreement rentalAgreement = checkoutService.processCheckOut(checkoutRequest);
            log.info("Checkout processed successfully for request: {}", checkoutRequest);
            return ResponseEntity.ok(rentalAgreementDTOMapper.entityToDto(rentalAgreement));
        } catch (Exception e) {
            log.error("Error processing checkout for request: {}. Error: {}", checkoutRequest, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
