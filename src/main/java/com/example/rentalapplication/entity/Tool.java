package com.example.rentalapplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tool")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(unique = true, nullable = false, name = "tool_code")
    private String toolCode;

    @Column(nullable = false, name = "tool_type")
    private String toolType;

    @Column(nullable = false)
    private String brand;

    public Tool(String toolCode, String toolType, String brand) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
    }
}
