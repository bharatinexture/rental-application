package com.example.rentalapplication.service;

import com.example.rentalapplication.entity.ToolTypes;
import com.example.rentalapplication.exception.ResourceNotFoundException;
import com.example.rentalapplication.repository.ToolTypesRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ToolTypeService {

    @Autowired
    private ToolTypesRepository toolTypesRepository;

    public List<ToolTypes> getAllToolTypes() {
        log.info("Fetching all tool types");
        return toolTypesRepository.findAll();
    }

    public ToolTypes getToolTypeByName(String typeName) {
        log.info("Fetching tool type: {}", typeName);
        return toolTypesRepository.findById(typeName)
                .orElseThrow(() -> {
                    log.error("Tool type not found with name: {}", typeName);
                    return new ResourceNotFoundException("Tool type not found with name: " + typeName);
                });
    }

    public ToolTypes createToolType(ToolTypes toolType) throws BadRequestException {
        log.info("Creating tool type with name: {}", toolType.getType());
        if (toolTypesRepository.existsById(toolType.getType())) {
            throw new BadRequestException("Tool type with name '" + toolType.getType() + "' already exists.");
        }
        return toolTypesRepository.save(toolType);
    }

    public ToolTypes updateToolType(String typeName, ToolTypes toolTypeDetails) {
        log.info("Updating tool type with name: {}", typeName);
        ToolTypes toolType = toolTypesRepository.findById(typeName)
                .orElseThrow(() -> new ResourceNotFoundException("Tool type not found with name: " + typeName));

        toolType.setDailyRentalCharge(toolTypeDetails.getDailyRentalCharge());
        toolType.setWeekdayCharge(toolTypeDetails.getWeekdayCharge());
        toolType.setWeekendCharge(toolTypeDetails.getWeekendCharge());
        toolType.setHolidayCharge(toolTypeDetails.getHolidayCharge());

        ToolTypes updatedToolType = toolTypesRepository.save(toolType);
        log.info("Tool type updated: {}", updatedToolType);
        return updatedToolType;
    }

    public void deleteToolType(String typeName) {
        log.info("Deleting tool type with name: {}", typeName);
        ToolTypes toolType = toolTypesRepository.findById(typeName)
                .orElseThrow(() -> {
                    log.error("Tool type not found with name: {}", typeName);
                    return new ResourceNotFoundException("Tool type not found with name: " + typeName);
                });
        toolTypesRepository.delete(toolType);
        log.info("Tool type deleted: {}", typeName);
    }
}
