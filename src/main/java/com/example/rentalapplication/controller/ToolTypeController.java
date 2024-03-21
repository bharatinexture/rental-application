package com.example.rentalapplication.controller;

import com.example.rentalapplication.entity.ToolTypes;
import com.example.rentalapplication.exception.ResourceNotFoundException;
import com.example.rentalapplication.service.ToolTypeService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool-types")
public class ToolTypeController {

    @Autowired
    private ToolTypeService toolTypesService;

    @GetMapping
    public ResponseEntity<List<ToolTypes>> getAllToolTypes() {
        List<ToolTypes> toolTypes = toolTypesService.getAllToolTypes();
        return new ResponseEntity<>(toolTypes, HttpStatus.OK);
    }

    @GetMapping("/{typeName}")
    public ResponseEntity<ToolTypes> getToolTypeByName(@PathVariable("typeName") String typeName) {
        try {
            ToolTypes toolType = toolTypesService.getToolTypeByName(typeName);
            return new ResponseEntity<>(toolType, HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ToolTypes> createToolType(@RequestBody ToolTypes toolType) throws BadRequestException {
        ToolTypes createdToolType = toolTypesService.createToolType(toolType);
        return new ResponseEntity<>(createdToolType, HttpStatus.CREATED);
    }

    @PutMapping("/{typeName}")
    public ResponseEntity<ToolTypes> updateToolType(@PathVariable("typeName") String typeName, @RequestBody ToolTypes toolTypeDetails) {
        try {
            ToolTypes updatedToolType = toolTypesService.updateToolType(typeName, toolTypeDetails);
            return new ResponseEntity<>(updatedToolType, HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{typeName}")
    public ResponseEntity<Void> deleteToolType(@PathVariable("typeName") String typeName) {
        try {
            toolTypesService.deleteToolType(typeName);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
