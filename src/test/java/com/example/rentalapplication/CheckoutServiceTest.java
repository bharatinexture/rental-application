package com.example.rentalapplication;

import com.example.rentalapplication.dto.CheckoutRequest;
import com.example.rentalapplication.model.RentalAgreement;
import com.example.rentalapplication.service.CheckoutService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test suite for the CheckoutService class.
 * Validates the checkout process by ensuring rental agreements are correctly created and
 * that proper exceptions are thrown for invalid input.
 * All the inputs are as specified in the requirement spec.
 */
@SpringBootTest
public class CheckoutServiceTest {

    @Autowired
    private CheckoutService checkoutService;

    /**
     * Test 1: to ensure the service throws a ValidationException when the discount percent is above 100 .
     */
    @Test
    void rentAgreementTest1(){
        CheckoutRequest request = new CheckoutRequest("JAKR",101, "09/03/15",5);
        assertThrows(ValidationException.class, () -> checkoutService.processCheckOut(request));
    }

    /**
     * Test 2: checkout with toolCode: LADW, discountPercent: 10, checkoutDate: 07/02/20 and rentalDays: 3.
     */
    @Test
    void rentAgreementTest2(){
        CheckoutRequest request = new CheckoutRequest("LADW",10, "07/02/20",3);
        RentalAgreement rentalAgreement = checkoutService.processCheckOut(request);
        assertEquals(rentalAgreement.getToolCode(), "LADW");
        assertEquals(rentalAgreement.getToolType(), "Ladder");
        assertEquals(rentalAgreement.getBrand(), "Werner");
        assertEquals(rentalAgreement.getRentalDays(), 3);
        assertEquals(rentalAgreement.getCheckOutDate(), LocalDate.of(2020, 7, 2));
        assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2020, 7, 5));
        assertEquals(rentalAgreement.getDailyRentalCharge(), new BigDecimal("1.99"));
        assertEquals(rentalAgreement.getChargedDays(), 2);
        assertEquals(rentalAgreement.getPreDiscountCharge(), new BigDecimal("3.98"));
        assertEquals(rentalAgreement.getDiscountPercent(), 10);
        assertEquals(rentalAgreement.getDiscountAmount(), new BigDecimal("0.40"));
        assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("3.58"));
    }

    /**
     * Test 3: checkout with toolCode: CHNS, discountPercent: 25, checkoutDate: 07/02/15 and rentalDays: 5.
     */
    @Test
    void rentAgreementTest3(){
        CheckoutRequest request =new CheckoutRequest("CHNS",25, "07/02/15",5);
        RentalAgreement rentalAgreement = checkoutService.processCheckOut(request);
        assertEquals(rentalAgreement.getToolCode(), "CHNS");
        assertEquals(rentalAgreement.getToolType(), "Chainsaw");
        assertEquals(rentalAgreement.getBrand(), "Stihl");
        assertEquals(rentalAgreement.getRentalDays(), 5);
        assertEquals(rentalAgreement.getCheckOutDate(), LocalDate.of(2015, 7, 2));
        assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2015, 7, 7));
        assertEquals(rentalAgreement.getDailyRentalCharge(), new BigDecimal("1.49"));
        assertEquals(rentalAgreement.getChargedDays(), 3);
        assertEquals(rentalAgreement.getPreDiscountCharge(), new BigDecimal("4.47"));
        assertEquals(rentalAgreement.getDiscountPercent(), 25);
        assertEquals(rentalAgreement.getDiscountAmount(), new BigDecimal("1.12"));
        assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("3.35"));
    }

    /**
     * Test 4: checkout with toolCode: JAKD, discountPercent: 0, checkoutDate: 09/03/15 and rentalDays: 6.
     */
    @Test
    void rentAgreementTest4(){
        CheckoutRequest request = new CheckoutRequest("JAKD",0, "09/03/15",6);
        RentalAgreement rentalAgreement = checkoutService.processCheckOut(request);
        assertEquals(rentalAgreement.getToolCode(), "JAKD");
        assertEquals(rentalAgreement.getToolType(), "Jackhammer");
        assertEquals(rentalAgreement.getBrand(), "DeWalt");
        assertEquals(rentalAgreement.getRentalDays(), 6);
        assertEquals(rentalAgreement.getCheckOutDate(), LocalDate.of(2015, 9, 3));
        assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2015, 9, 9));
        assertEquals(rentalAgreement.getDailyRentalCharge(), new BigDecimal("2.99"));
        assertEquals(rentalAgreement.getChargedDays(), 3);
        assertEquals(rentalAgreement.getPreDiscountCharge(), new BigDecimal("8.97"));
        assertEquals(rentalAgreement.getDiscountPercent(), 0);
        assertEquals(rentalAgreement.getDiscountAmount(), new BigDecimal("0.00"));
        assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("8.97"));
    }

    /**
     * Test 5: checkout with toolCode: JAKR, discountPercent: 0, checkoutDate: 07/02/15 and rentalDays: 9.
     */
    @Test
    void rentAgreementTest5(){
        CheckoutRequest request = new CheckoutRequest("JAKR",0, "07/02/15",9);
        RentalAgreement rentalAgreement = checkoutService.processCheckOut(request);
        assertEquals(rentalAgreement.getToolCode(), "JAKR");
        assertEquals(rentalAgreement.getToolType(), "Jackhammer");
        assertEquals(rentalAgreement.getBrand(), "Ridgid");
        assertEquals(rentalAgreement.getRentalDays(), 9);
        assertEquals(rentalAgreement.getCheckOutDate(), LocalDate.of(2015, 7, 2));
        assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2015, 7, 11));
        assertEquals(rentalAgreement.getDailyRentalCharge(), new BigDecimal("2.99"));
        assertEquals(rentalAgreement.getChargedDays(), 5);
        assertEquals(rentalAgreement.getPreDiscountCharge(), new BigDecimal("14.95"));
        assertEquals(rentalAgreement.getDiscountPercent(), 0);
        assertEquals(rentalAgreement.getDiscountAmount(), new BigDecimal("0.00"));
        assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("14.95"));
    }

    /**
     * Test 6: checkout with toolCode: JAKR, discountPercent: 50, checkoutDate: 07/02/20 and rentalDays: 4.
     */
    @Test
    void rentAgreementTest6(){
        CheckoutRequest request = new CheckoutRequest("JAKR",50, "07/02/20",4);
        RentalAgreement rentalAgreement = checkoutService.processCheckOut(request);
        assertEquals(rentalAgreement.getToolCode(), "JAKR");
        assertEquals(rentalAgreement.getToolType(), "Jackhammer");
        assertEquals(rentalAgreement.getBrand(), "Ridgid");
        assertEquals(rentalAgreement.getRentalDays(), 4);
        assertEquals(rentalAgreement.getCheckOutDate(), LocalDate.of(2020, 7, 2));
        assertEquals(rentalAgreement.getDueDate(), LocalDate.of(2020, 7, 6));
        assertEquals(rentalAgreement.getDailyRentalCharge(), new BigDecimal("2.99"));
        assertEquals(rentalAgreement.getChargedDays(), 1);
        assertEquals(rentalAgreement.getPreDiscountCharge(), new BigDecimal("2.99"));
        assertEquals(rentalAgreement.getDiscountPercent(), 50);
        assertEquals(rentalAgreement.getDiscountAmount(), new BigDecimal("1.50"));
        assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("1.49"));
    }

    /**
     * Test 7: to ensure the service throws a ValidationException when the discountPercent is out of the acceptable range,
     * and the rental days are set to zero.
     */
    @Test
    void invalidRentalDaysTest(){
        CheckoutRequest request = new CheckoutRequest("JAKR",111, "09/03/15",0);
        assertThrows(ValidationException.class, () -> checkoutService.processCheckOut(request));
    }
}