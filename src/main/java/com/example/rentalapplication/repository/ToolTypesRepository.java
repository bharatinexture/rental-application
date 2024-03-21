package com.example.rentalapplication.repository;

import com.example.rentalapplication.entity.ToolTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolTypesRepository extends JpaRepository<ToolTypes,String> {

    ToolTypes findByType(String type);
}
