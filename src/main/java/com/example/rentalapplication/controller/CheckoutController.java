package com.example.rentalapplication.controller;

import com.example.rentalapplication.DTO.CheckoutRequest;
import com.example.rentalapplication.DTO.RentalAgreement;
import com.example.rentalapplication.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity<Object> processCheckout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        try {
            RentalAgreement rentalAgreement = checkoutService.processCheckOut(checkoutRequest);
            return ResponseEntity.ok(rentalAgreement);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
