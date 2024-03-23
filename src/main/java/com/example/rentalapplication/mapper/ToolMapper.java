package com.example.rentalapplication.mapper;

import com.example.rentalapplication.DTO.ToolDTO;
import com.example.rentalapplication.entity.Tool;

public class ToolMapper {

    public static Tool dtoToEntity (ToolDTO toolDTO) {

        return Tool.builder()
                .toolCode(toolDTO.getToolCode())
                .toolType(toolDTO.getToolType())
                .brand(toolDTO.getBrand())
                .build();
    }
}
