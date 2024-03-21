package com.example.rentalapplication.controller;

import com.example.rentalapplication.entity.Tool;
import com.example.rentalapplication.exception.ResourceNotFoundException;
import com.example.rentalapplication.service.ToolService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    private ToolService toolService;

    @GetMapping
    public ResponseEntity<List<Tool>> getAllTools() {
        List<Tool> tools = toolService.getTools();
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable("id") Long toolId) {
        try {
            Tool tool = toolService.getToolById(toolId);
            return new ResponseEntity<>(tool, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool) throws BadRequestException {
        Tool createdTool = toolService.createTool(tool);
        return new ResponseEntity<>(createdTool, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateTool(@PathVariable("id") Long toolId, @RequestBody Tool toolDetails) {
        try {
            Tool updatedTool = toolService.updateTool(toolId, toolDetails);
            return new ResponseEntity<>(updatedTool, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTool(@PathVariable("id") Long toolId) {
        try {
            toolService.deleteTool(toolId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tool with id "+ toolId +" does not exist");
        }
    }
}
