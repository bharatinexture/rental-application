package com.example.rentalapplication;

import com.example.rentalapplication.DTO.CheckoutRequest;
import com.example.rentalapplication.entity.RentalAgreement;
import com.example.rentalapplication.entity.Tool;
import com.example.rentalapplication.entity.ToolTypes;
import com.example.rentalapplication.repository.ToolRepository;
import com.example.rentalapplication.repository.ToolTypesRepository;
import com.example.rentalapplication.service.CheckoutService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CheckoutServiceTest {

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private ToolTypesRepository toolTypesRepository;

    @Autowired
    private CheckoutService checkoutService;

    // Test 1
    @Test
    void invalidDiscountTest(){
        CheckoutRequest request = new CheckoutRequest("JAKR",101, LocalDate.of(2015,9,3),5);
        assertThrows(ValidationException.class, () -> checkoutService.processCheckOut(request));
    }

    @Test
    void invalidRentalDaysTest(){
        CheckoutRequest request = new CheckoutRequest("JAKR",101, LocalDate.of(2015,9,3),0);
        assertThrows(ValidationException.class, () -> checkoutService.processCheckOut(request));
    }

    // Test 2
    @Test
    void rentAgreementTest2(){
        CheckoutRequest request = new CheckoutRequest("LADW",10, LocalDate.of(2020,7,2),3);
        Tool tool = getTools().get(request.getToolCode());
        Mockito.when(toolRepository.findToolByToolCode(request.getToolCode())).thenReturn(tool);
        Mockito.when(toolTypesRepository.findByType(Mockito.any())).thenReturn(getToolTypes().get(tool.getToolType()));
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

    // Test 3
    @Test
    void rentAgreementTest3(){
        CheckoutRequest request =new CheckoutRequest("CHNS",25, LocalDate.of(2015,7,2),5);
        Tool tool = getTools().get(request.getToolCode());
        Mockito.when(toolRepository.findToolByToolCode(request.getToolCode())).thenReturn(tool);
        Mockito.when(toolTypesRepository.findByType(Mockito.any())).thenReturn(getToolTypes().get(tool.getToolType()));
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

    // Test 4
    @Test
    void rentAgreementTest4(){
        CheckoutRequest request = new CheckoutRequest("JAKD",0, LocalDate.of(2015,9,3),6);
        Tool tool = getTools().get(request.getToolCode());
        Mockito.when(toolRepository.findToolByToolCode(request.getToolCode())).thenReturn(tool);
        Mockito.when(toolTypesRepository.findByType(Mockito.any())).thenReturn(getToolTypes().get(tool.getToolType()));
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

    // Test 5
    @Test
    void rentAgreementTest5(){
        CheckoutRequest request = new CheckoutRequest("JAKR",0, LocalDate.of(2015,7,2),9);
        Tool tool = getTools().get(request.getToolCode());
        Mockito.when(toolRepository.findToolByToolCode(request.getToolCode())).thenReturn(tool);
        Mockito.when(toolTypesRepository.findByType(Mockito.any())).thenReturn(getToolTypes().get(tool.getToolType()));
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

    // Test 6
    @Test
    void rentAgreementTest6(){
        CheckoutRequest request = new CheckoutRequest("JAKR",50, LocalDate.of(2020,7,2),4);
        Tool tool = getTools().get(request.getToolCode());
        Mockito.when(toolRepository.findToolByToolCode(request.getToolCode())).thenReturn(tool);
        Mockito.when(toolTypesRepository.findByType(Mockito.any())).thenReturn(getToolTypes().get(tool.getToolType()));
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

    // utility Map to get Tools without interacting with the database
    public Map<String, Tool>  getTools(){
        Map<String,Tool> tools = new HashMap<>();
        tools.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl"));
        tools.put("LADW", new Tool("LADW", "Ladder", "Werner"));
        tools.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt"));
        tools.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid"));
        return tools;
    }

    // utility Map to get the ToolTypes without interacting with the database
    public Map<String, ToolTypes> getToolTypes(){
        Map<String,ToolTypes> toolTypes = new HashMap<>();
        toolTypes.put("Ladder", new ToolTypes("Ladder", new BigDecimal("1.99") , true, true, false));
        toolTypes.put("Chainsaw", new ToolTypes("Chainsaw", new BigDecimal("1.49") , true, false, true));
        toolTypes.put("Jackhammer", new ToolTypes("Jackhammer", new BigDecimal("2.99") , true, false, false));
        return toolTypes;
    }

}
