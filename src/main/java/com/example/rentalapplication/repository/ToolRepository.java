package com.example.rentalapplication.repository;

import com.example.rentalapplication.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {

    Tool findToolByToolCode(String toolCode);

    boolean existsByToolCode(String toolCode);
}
