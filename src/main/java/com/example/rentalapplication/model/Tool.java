package com.example.rentalapplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a tool available for rental. It captures essential details about the tool,
 * such as its code, type, and brand. This class is used to identify and manage the different
 * tools that can be rented from the store.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tool {

    /**
     * Unique code identifying the tool. This code is used as an identifier
     * for rental transactions and tracking.
     */
    private String toolCode;
    /**
     * The type of the tool, which is linked to the pricing and charging policies
     * for the tool's rental.
     */
    private String toolType;
    /**
     * The brand of the tool, providing information on the tool's manufacturer/marketer.
     */
    private String brand;

}