package com.example.rentalapplication;

import com.example.rentalapplication.DTO.CheckoutRequest;
import com.example.rentalapplication.entity.Tool;
import com.example.rentalapplication.entity.ToolTypes;
import com.example.rentalapplication.repository.ToolRepository;
import com.example.rentalapplication.repository.ToolTypesRepository;
import com.example.rentalapplication.service.CheckoutService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class RentalApplicationTests {
    @Test
	void contextLoads() {
	}



}
