package com.example.rentalapplication.util;

import com.example.rentalapplication.model.Tool;
import com.example.rentalapplication.model.ToolTypes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that simulates a simple in-memory database for the rental application.
 * It provides static data structures/collections that hold information about tool types and individual tools,
 * initializing them with the data provided in the requirement spec.
 */
public class DBUtils {
    // Static map variables

    /**
     * A map of tool types keyed by the tool type name. It holds predefined characteristics
     * of each tool type, such as daily rental charge and charges applicable on weekdays,
     * weekends, and holidays.
     */
    public static Map<String, ToolTypes> toolTypes;
    /**
     * A map of tools keyed by the tool code. It holds tool instances with attributes
     * such as tool code, type, and brand.
     */
    public static Map<String, Tool> tools;

    // Static block to initialize the static variables
    static {
        toolTypes = new HashMap<>();
        toolTypes.put("Ladder", new ToolTypes("Ladder", new BigDecimal("1.99") , true, true, false));
        toolTypes.put("Chainsaw", new ToolTypes("Chainsaw", new BigDecimal("1.49") , true, false, true));
        toolTypes.put("Jackhammer", new ToolTypes("Jackhammer", new BigDecimal("2.99") , true, false, false));

        tools = new HashMap<>();
        tools.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl"));
        tools.put("LADW", new Tool("LADW", "Ladder", "Werner"));
        tools.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt"));
        tools.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid"));
    }

}
