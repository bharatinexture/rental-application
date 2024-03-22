package com.example.rentalapplication.controller;

import com.example.rentalapplication.entity.ToolTypes;
import com.example.rentalapplication.exception.ResourceNotFoundException;
import com.example.rentalapplication.service.ToolTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/tool-types")
public class ToolTypeController {

    @Autowired
    private ToolTypeService toolTypesService;

    /**
     * Retrieves all tool types available in the system.
     *
     * @return a {@link ResponseEntity} containing a list of {@link ToolTypes} and the HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<ToolTypes>> getAllToolTypes() {
        log.info("Fetching all tool types");
        List<ToolTypes> toolTypes = toolTypesService.getAllToolTypes();
        log.info("Fetched {} tool types", toolTypes.size());
        return new ResponseEntity<>(toolTypes, HttpStatus.OK);
    }

    /**
     * Retrieves a specific tool type by its name.
     *
     * @param typeName the name of the tool type to retrieve
     * @return a {@link ResponseEntity} containing the found {@link ToolTypes} or the HTTP status NOT FOUND
     */
    @GetMapping("/{typeName}")
    public ResponseEntity<ToolTypes> getToolTypeByName(@PathVariable("typeName") String typeName) {
        log.info("Fetching tool type with name: {}", typeName);
        try {
            ToolTypes toolType = toolTypesService.getToolTypeByName(typeName);
            log.info("Found tool type: {}", typeName);
            return new ResponseEntity<>(toolType, HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            log.error("Tool type with name {} not found", typeName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new tool type.
     *
     * @param toolType the {@link ToolTypes} to create
     * @return a {@link ResponseEntity} containing the created {@link ToolTypes} and the HTTP status CREATED
     * @throws BadRequestException if the creation fails due to validation errors or other issues
     */
    @PostMapping
    public ResponseEntity<ToolTypes> createToolType(@RequestBody ToolTypes toolType) throws BadRequestException {
        log.info("Creating new tool type with type: {}", toolType.getType());
        try {
            ToolTypes createdToolType = toolTypesService.createToolType(toolType);
            log.info("Created tool type with type: {}", toolType.getType());
            return new ResponseEntity<>(createdToolType, HttpStatus.CREATED);
        } catch (BadRequestException ex) {
            log.error("Failed to create tool type: {}", ex.getMessage());
            throw ex;
        }
    }

    /**
     * Updates an existing tool type identified by its name.
     *
     * @param typeName the name of the tool type to update
     * @param toolTypeDetails the updated tool type details
     * @return a {@link ResponseEntity} containing the updated {@link ToolTypes} or the HTTP status NOT FOUND
     */
    @PutMapping("/{typeName}")
    public ResponseEntity<ToolTypes> updateToolType(@PathVariable("typeName") String typeName, @RequestBody ToolTypes toolTypeDetails) {
        log.info("Updating tool type with name: {}", typeName);
        try {
            ToolTypes updatedToolType = toolTypesService.updateToolType(typeName, toolTypeDetails);
            log.info("Updated tool type with name: {}", typeName);
            return new ResponseEntity<>(updatedToolType, HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            log.error("Tool type with name {} not found for update", typeName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a tool type identified by its name.
     *
     * @param typeName the name of the tool type to delete
     * @return a {@link ResponseEntity} containing the HTTP status OK if deletion is successful, or NOT FOUND if the tool type does not exist
     */
    @DeleteMapping("/{typeName}")
    public ResponseEntity<Void> deleteToolType(@PathVariable("typeName") String typeName) {
        log.info("Deleting tool type with name: {}", typeName);
        try {
            toolTypesService.deleteToolType(typeName);
            log.info("Deleted tool type with name: {}", typeName);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ResourceNotFoundException ex) {
            log.error("Tool type with name {} not found for deletion", typeName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
