package com.example.rentalapplication.service;

import com.example.rentalapplication.entity.ToolTypes;
import com.example.rentalapplication.exception.ResourceNotFoundException;
import com.example.rentalapplication.repository.ToolTypesRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolTypeService {

    @Autowired
    private ToolTypesRepository toolTypesRepository;

    public List<ToolTypes> getAllToolTypes() {
        return toolTypesRepository.findAll();
    }

    public ToolTypes getToolTypeByName(String typeName) {
        return toolTypesRepository.findById(typeName)
                .orElseThrow(() -> new ResourceNotFoundException("Tool type not found with name: " + typeName));
    }

    public ToolTypes createToolType(ToolTypes toolType) throws BadRequestException {
        if (toolTypesRepository.existsById(toolType.getType())) {
            throw new BadRequestException("Tool type with name '" + toolType.getType() + "' already exists.");
        }
        return toolTypesRepository.save(toolType);
    }

    public ToolTypes updateToolType(String typeName, ToolTypes toolTypeDetails) {
        ToolTypes toolType = toolTypesRepository.findById(typeName)
                .orElseThrow(() -> new ResourceNotFoundException("Tool type not found with name: " + typeName));

        toolType.setDailyRentalCharge(toolTypeDetails.getDailyRentalCharge());
        toolType.setWeekdayCharge(toolTypeDetails.getWeekdayCharge());
        toolType.setWeekendCharge(toolTypeDetails.getWeekendCharge());
        toolType.setHolidayCharge(toolTypeDetails.getHolidayCharge());

        return toolTypesRepository.save(toolType);
    }

    public void deleteToolType(String typeName) {
        ToolTypes toolType = toolTypesRepository.findById(typeName)
                .orElseThrow(() -> new ResourceNotFoundException("Tool type not found with name: " + typeName));

        toolTypesRepository.delete(toolType);
    }
}
