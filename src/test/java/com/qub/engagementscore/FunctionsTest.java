package com.qub.engagementscore;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class FunctionsTest {
    
    // TESTING THE parameterChecker

    @Test
    public void testEmptyItemName() {
        String[] items = { "Session1", "", "Session3" };
        String[] attendances = { "5", "6", "7", "8" };
        String[] totalHours = { "10", "10", "10", "10" };

        Map<String, Object> result = Functions.parameterChecker(items, attendances, totalHours);
        assertTrue((Boolean) result.get("error"));
        assertEquals("Item names cannot be empty.", result.get("message"));
    }

    @Test
    public void testAttendancesLessThanFour() {
        String[] items = { "Session1", "Session2", "Session3" };
        String[] attendances = { "5", "6", "7" };
        String[] totalHours = { "10", "10", "10", "10" };

        Map<String, Object> result = Functions.parameterChecker(items, attendances, totalHours);
        assertTrue((Boolean) result.get("error"));
        assertEquals("Attendances array must have at least 4 values.", result.get("message"));
    }

    @Test
    public void testTotalHoursLessThanFour() {
        String[] items = { "Session1", "Session2", "Session3" };
        String[] attendances = { "5", "6", "7", "8" };
        String[] totalHours = { "10", "10", "10" };
    
        Map<String, Object> result = Functions.parameterChecker(items, attendances, totalHours);
        assertTrue((Boolean) result.get("error"));
        assertEquals("Total hours array must have at least 4 values.", result.get("message"));
    }
    
    @Test
    public void testNonIntegerValues() {
        String[] items = { "Session1", "Session2", "Session3" };
        String[] attendances = { "5", "6", "seven", "3" };
        String[] totalHours = { "10", "ten", "10", "30" };
    
        Map<String, Object> result = Functions.parameterChecker(items, attendances, totalHours);
        assertTrue((Boolean) result.get("error"));
        assertEquals("Attendance and total hours must be integers.", result.get("message"));
    }
    
    @Test
    public void testNegativeAttendance() {
        String[] items = { "Session1", "Session2", "Session3" };
        String[] attendances = { "5", "-6", "7", "8" };
        String[] totalHours = { "10", "10", "10", "10" };
    
        Map<String, Object> result = Functions.parameterChecker(items, attendances, totalHours);
        assertTrue((Boolean) result.get("error"));
        assertEquals("Attendance hours cannot be negative.", result.get("message"));
    }
    
    @Test
    public void testNegativeTotalHours() {
        String[] items = { "Session1", "Session2", "Session3" };
        String[] attendances = { "5", "6", "7", "8" };
        String[] totalHours = { "10", "-10", "10", "10" };
    
        Map<String, Object> result = Functions.parameterChecker(items, attendances, totalHours);
        assertTrue((Boolean) result.get("error"));
        assertEquals("Total hours cannot be negative.", result.get("message"));
    }

    @Test
    public void testExceedingAttendanceHours() {
        String[] items = { "Session1", "Session2", "Session3" };
        String[] attendances = { "5", "11", "7", "8" };
        String[] totalHours = { "10", "10", "10", "10" };
    
        Map<String, Object> result = Functions.parameterChecker(items, attendances, totalHours);
        assertTrue((Boolean) result.get("error"));
        assertEquals("Attendance hours cannot exceed total assigned hours.", result.get("message"));
    }
    

    // TESTING THE getEngagementScore FUNCTION
    // ChatGPT with Wolfram was handy for quickly working out calculations

    @Test
    public void testHappyPath() {
        int[] attendances = {17, 11, 22, 40};
        int[] totalHours = {34, 22, 44, 80};
        int result = Functions.getEngagementScore(attendances, totalHours);
        assertEquals(50, result);
    }

    @Test
    public void testZeroTotalHours() {
        int[] attendances = {10, 20, 30, 40};
        int[] totalHours = {100, 0, 60, 80}; // Second total hour is zero

        // Weights: 0.3, 0.4, 0.15, 0.15
        // Calculation: (10 * 0.3 / 100) + (30 * 0.15 / 60) + (40 * 0.15 / 80) = 0.03 + 0.075 + 0.075 = 0.18
        int result = Functions.getEngagementScore(attendances, totalHours);
        assertEquals(18, result); // Expecting 18 as the result
    }

    @Test
    public void testNegativeValues() {
        int[] attendances = {30, -30, 30, 30};
        int[] totalHours = {100, 100, 100, 100};
    
        //Weights: 0.3, 0.4, 0.15, 0.15
        // Calculation:
        // (30 * 0.3 / 100) + (-30 * 0.4 / 100) + (30 * 0.15 / 100) + (30 * 0.15 / 100)
        // = 0.09 - 0.12 + 0.045 + 0.045 = 0.06
        int result = Functions.getEngagementScore(attendances, totalHours);
        assertEquals(6, result); // Expecting 6 as the result
    }
    

    @Test
    public void testFloatingPointAccuracy() {
        int[] attendances = {10, 20, 30, 40};
        int[] totalHours = {333, 50, 60, 80}; // Adjusted to test floating-point behavior
        int result = Functions.getEngagementScore(attendances, totalHours);
        assertEquals(32, result); // Calculated based on the provided weights
    }

}
