package com.example.rentalapplication.service;

import com.example.rentalapplication.entity.Tool;
import com.example.rentalapplication.exception.ResourceNotFoundException;
import com.example.rentalapplication.repository.ToolRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public List<Tool> getTools(){
        return toolRepository.findAll();
    }

    public Tool getToolById(Long toolId){
        return toolRepository.findById(toolId)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + toolId));
    }

    public Tool createTool(Tool tool) throws BadRequestException {
        if (toolRepository.existsByToolCode(tool.getToolCode())) {
            throw new BadRequestException("Tool with code '" + tool.getToolCode() + "' already exists.");
        }
        return toolRepository.save(tool);
    }

    public Tool updateTool(Long toolId, Tool toolDetails) throws BadRequestException {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + toolId));

        if (!tool.getToolCode().equals(toolDetails.getToolCode()) && toolRepository.existsByToolCode(toolDetails.getToolCode())) {
            throw new BadRequestException("Tool with code '" + toolDetails.getToolCode() + "' already exists.");
        }
        tool.setToolCode(toolDetails.getToolCode());
        tool.setToolType(toolDetails.getToolType());
        tool.setBrand(toolDetails.getBrand());

        return toolRepository.save(tool);
    }

    public void deleteTool(Long toolId) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + toolId));

        toolRepository.delete(tool);
    }
}
