package com.example.rentalapplication.consoleentry;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.rentalapplication.service.CheckoutService;
import com.example.rentalapplication.dto.CheckoutRequest;
import com.example.rentalapplication.model.RentalAgreement;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class ConsoleCommandListener implements CommandLineRunner {

    private final CheckoutService checkoutService;

    public ConsoleCommandListener(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to the Tool Rental Application!");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("\nTo checkout a tool, please provide the following information.");
                System.out.println("Type 'exit' anytime to quit.");

                System.out.print("Tool code: ");
                String toolCode = reader.readLine();
                if ("exit".equalsIgnoreCase(toolCode)) break;

                System.out.print("Discount percent (0-100): ");
                String discountStr = reader.readLine();
                if ("exit".equalsIgnoreCase(discountStr)) break;
                int discountPercent = Integer.parseInt(discountStr);

                System.out.print("Checkout date (MM/dd/yy): ");
                String checkOutDate = reader.readLine();
                if ("exit".equalsIgnoreCase(checkOutDate)) break;

                System.out.print("Rental day count: ");
                String rentalDayCountStr = reader.readLine();
                if ("exit".equalsIgnoreCase(rentalDayCountStr)) break;
                int rentalDayCount = Integer.parseInt(rentalDayCountStr);

                try {
                    CheckoutRequest request = new CheckoutRequest(toolCode, discountPercent, checkOutDate, rentalDayCount);
                    RentalAgreement rentalAgreement = checkoutService.processCheckOut(request);
                    System.out.println("Rental Agreement Generated: ");
                    // Assuming RentalAgreement has a toString method formatted for printing, or you can format the output as needed
                    System.out.println(rentalAgreement);
                } catch (Exception e) {
                    System.out.println("Error processing checkout: " + e.getMessage());
                }
            }
        }
    }
}
