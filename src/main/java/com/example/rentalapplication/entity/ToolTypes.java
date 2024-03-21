package com.example.rentalapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tool_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolTypes {

    @Id
    private String type;

    @Column(nullable = false, name = "daily_rental_charge")
    private BigDecimal dailyRentalCharge;

    @Column(nullable = false, name = "weekday_charge")
    private Boolean weekdayCharge;

    @Column(nullable = false, name = "weekend_charge")
    private Boolean weekendCharge;

    @Column(nullable = false, name = "holiday_charge")
    private Boolean holidayCharge;

}
