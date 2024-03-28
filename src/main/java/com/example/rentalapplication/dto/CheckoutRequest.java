package com.example.rentalapplication.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The DTO representing a request to check out/rent a tool.
 * It encapsulates the necessary information needed to initiate a tool rental process.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {

    /**
     * The unique code identifying the tool to be rented.
     */
    @NotNull
    @NotBlank
    @NotEmpty
    private String toolCode;

    /**
     * The discount percentage to be applied to the rental.
     * The value must be between 0 and 100, both inclusive.
     */
    @NotNull
    @Max(value=100, message = "Discount percent must be between 0 and 100.")
    @Min(value=0, message = "Discount percent must be between 0 and 100.")
    private Integer discountPercent;

    /**
     * The date when the tool rental is checked out, formatted as MM/dd/yy.
     * This field must match the specified date format pattern.
     */
    @NotNull
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{2}$", message = "Invalid date format. Expected format: MM/dd/yy")
    private String checkOutDate;

    /**
     * The number of days for which the tool is rented.
     * This value must be greater than or equal to 1 (the tool must be rented for at least a day).
     */
    @NotNull
    @Min(value=1, message = "Rental day count must be 1 or greater.")
    private Integer rentalDayCount;
}