package com.example.rentalapplication.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private String toolCode;

    @NotNull
    @Max(value=100, message = "Discount percent must be between 0 and 100.")
    @Min(value=0, message = "Discount percent must be between 0 and 100.")
    private Integer discountPercent;

    @NotNull
    private LocalDate checkOutDate;

    @NotNull
    @Min(value=1, message = "Rental day count must be 1 or greater.")
    private Integer rentalDayCount;

}