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
    void testValidInput() {
        String[] items = {"Lecture", "Lab", "Support", "Canvas"};
        int[] attendances = {2, 1, 2, 2};
        int[] total_hours = {33, 22, 44, 55};

        Map<String, Object> output = Functions.parameterChecker(items, attendances, total_hours);

        assertFalse((boolean) output.get("error"));
        assertArrayEquals(items, (Object[]) output.get("items"));
        assertArrayEquals(attendances, (int[]) output.get("attendance"));
    }

    @Test
    void testEmptyItemName() {
        String[] items = {"", "Lab", "Support", "Canvas"}; //Empty item
        int[] attendances = {2, 1, 2, 2};
        int[] total_hours = {33, 22, 44, 55};

        Map<String, Object> output = Functions.parameterChecker(items, attendances, total_hours);

        assertTrue((boolean) output.get("error"));
        assertArrayEquals(items, (Object[]) output.get("items"));
        assertArrayEquals(attendances, (int[]) output.get("attendance"));
    }

    @Test
    void testMissingAttendance() {
        String[] items = {"", "Lab", "Support", "Canvas"}; //Empty item
        int[] attendances = {2, 2, 2};
        int[] total_hours = {33, 22, 44, 55};

        Map<String, Object> output = Functions.parameterChecker(items, attendances, total_hours);

        assertTrue((boolean) output.get("error"));
        assertArrayEquals(items, (Object[]) output.get("items"));
        assertArrayEquals(attendances, (int[]) output.get("attendance"));
    }

    @Test
    void testMissingTotalHour() {
        String[] items = {"", "Lab", "Support", "Canvas"}; //Empty item
        int[] attendances = {2, 1, 2, 2};
        int[] total_hours = {33, 44, 55};

        Map<String, Object> output = Functions.parameterChecker(items, attendances, total_hours);

        assertTrue((boolean) output.get("error"));
        assertArrayEquals(items, (Object[]) output.get("items"));
        assertArrayEquals(attendances, (int[]) output.get("attendance"));
    }

    @Test
    void testAttendanceExceedsTotalHours() {
        String[] items = {"Lecture", "Lab", "Support", "Canvas"}; //Empty item
        int[] attendances = {200, 100, 200, 200};
        int[] total_hours = {33, 22, 44, 55};

        Map<String, Object> output = Functions.parameterChecker(items, attendances, total_hours);

        assertTrue((boolean) output.get("error"));
        assertArrayEquals(items, (Object[]) output.get("items"));
        assertArrayEquals(attendances, (int[]) output.get("attendance"));
    }

    @Test
    void testNegativeAttendance() {
        String[] items = {"", "Lab", "Support", "Canvas"}; //Empty item
        int[] attendances = {-2, -1, -2, 2};
        int[] total_hours = {33, 22, 44, 55};

        Map<String, Object> output = Functions.parameterChecker(items, attendances, total_hours);

        assertTrue((boolean) output.get("error"));
        assertArrayEquals(items, (Object[]) output.get("items"));
        assertArrayEquals(attendances, (int[]) output.get("attendance"));
    }

    @Test
    void testNegativeTotalHours() {
        String[] items = {"", "Lab", "Support", "Canvas"}; //Empty item
        int[] attendances = {2, 1, 2, 2};
        int[] total_hours = {-33, -22, -44, -55};

        Map<String, Object> output = Functions.parameterChecker(items, attendances, total_hours);

        assertTrue((boolean) output.get("error"));
        assertArrayEquals(items, (Object[]) output.get("items"));
        assertArrayEquals(attendances, (int[]) output.get("attendance"));
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
