package com.example.rentalapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "checkout")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "rental_day_count")
    private Integer rentalDayCount;

    @Column(nullable = false, name = "discount_percent")
    private Integer discountPercent;

    @Column(nullable = false, name = "check_out_date")
    private LocalDate checkOutDate;

    @ManyToOne
    @JoinColumn(name = "tool_code", referencedColumnName = "tool_code", nullable = false)
    private Tool tool;



}



