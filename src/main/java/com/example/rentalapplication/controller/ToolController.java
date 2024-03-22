package com.example.rentalapplication.controller;

import com.example.rentalapplication.entity.Tool;
import com.example.rentalapplication.exception.ResourceNotFoundException;
import com.example.rentalapplication.service.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool")
@Slf4j
public class ToolController {

    @Autowired
    private ToolService toolService;

    /**
     * Retrieves a list of all tools.
     *
     * @return a {@link ResponseEntity} containing a list of {@link Tool} instances and the HTTP status OK
     */
    @GetMapping
    public ResponseEntity<List<Tool>> getAllTools() {
        log.info("Fetching all tools");
        List<Tool> tools = toolService.getTools();
        log.info("Fetched {} tools", tools.size());
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }

    /**
     * Retrieves a single tool by its ID.
     * If the tool is found, it returns the tool with HTTP status OK.
     * If the tool is not found, it returns HTTP status NOT FOUND.
     *
     * @param toolId the ID of the tool to retrieve
     * @return a {@link ResponseEntity} containing the found {@link Tool} or the HTTP status NOT FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable("id") Long toolId) {
        try {
            log.info("Fetching tool with ID {}", toolId);
            Tool tool = toolService.getToolById(toolId);
            log.info("Found tool with ID {}", toolId);
            return new ResponseEntity<>(tool, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            log.error("Tool with ID {} not found", toolId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new tool.
     * Returns the created tool with HTTP status CREATED.
     *
     * @param tool the {@link Tool} to create
     * @return a {@link ResponseEntity} containing the created {@link Tool} and the HTTP status CREATED
     * @throws BadRequestException if the creation fails due to validation errors or other issues
     */
    @PostMapping
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool) throws BadRequestException {
        log.info("Creating new tool with code: {}", tool.getToolCode());
        try {
            Tool createdTool = toolService.createTool(tool);
            log.info("Created tool with ID {}", createdTool.getId());
            return new ResponseEntity<>(createdTool, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            log.error("Failed to create tool: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Updates an existing tool identified by its ID.
     *
     * @param toolId the ID of the tool to update
     * @param toolDetails the updated tool details
     * @return a {@link ResponseEntity} containing the updated {@link Tool} or the HTTP status NOT FOUND
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateTool(@PathVariable("id") Long toolId, @RequestBody Tool toolDetails) {
        log.info("Updating tool with ID {}", toolId);
        try {
            Tool updatedTool = toolService.updateTool(toolId, toolDetails);
            log.info("Updated tool with ID {}", toolId);
            return new ResponseEntity<>(updatedTool, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            log.error("Tool with ID {} not found for update", toolId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            log.error("Error updating tool with ID {}: {}", toolId, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a tool identified by its ID.
     *
     * @param toolId the ID of the tool to delete
     * @return a {@link ResponseEntity} containing the HTTP status OK if deletion is successful, or NOT FOUND if the tool does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTool(@PathVariable("id") Long toolId) {
        log.info("Deleting tool with ID {}", toolId);
        try {
            toolService.deleteTool(toolId);
            log.info("Deleted tool with ID {}", toolId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            log.error("Tool with ID {} not found for deletion", toolId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tool with id "+ toolId +" does not exist");
        }
    }
}
