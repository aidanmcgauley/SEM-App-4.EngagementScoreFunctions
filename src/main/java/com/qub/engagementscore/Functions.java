package com.qub.engagementscore;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://sem-frontend.40058902.qpc.hal.davecutting.uk/")

public class Functions {
    
    public static Map<String, Object> parameterChecker(String[] items, String[] attendances, String[] totalHours) {
        Map<String, Object> output = new HashMap<>();
        output.put("error", false);
        output.put("message", "");
        output.put("items", items);
        //output.put("attendance", attendances);
        //output.put("total_hours", totalHours);

        int[] attendancesInt = new int[attendances.length];
        int[] totalHoursInt = new int[totalHours.length];

        // Check if any session names are empty
        for (String item : items) {
            if (item == null || item.trim().isEmpty()) {
                output.put("error", true);
                output.put("message", "Item names cannot be empty.");
                return output;
            }
        }

        // Check if the attendances array has less than 4 values
        if (attendances.length < 4) {
            output.put("error", true);
            output.put("message", "Attendances array must have at least 4 values.");
            return output;
        }

        // Check if the totalHours array has less than 4 values
        if (totalHours.length < 4) {
            output.put("error", true);
            output.put("message", "Total hours array must have at least 4 values.");
            return output;
        }

        for (int i = 0; i < attendances.length; i++) {
            //int attendance;
            //int totalAssignedHours;
            try {
                attendancesInt[i] = Integer.parseInt(attendances[i]);
                totalHoursInt[i] = Integer.parseInt(totalHours[i]);
            } catch (NumberFormatException e) {
                output.put("error", true);
                output.put("message", "Attendance and total hours must be integers.");
                return output;
            }

            // Check if attendance is non-negative
            if (attendancesInt[i] < 0) {
                output.put("error", true);
                output.put("message", "Attendance hours cannot be negative.");
                return output;
            }

            // Check if total hours is non-negative
            if (totalHoursInt[i] < 0) {
                output.put("error", true);
                output.put("message", "Total hours cannot be negative.");
                return output;
            }

            // Check if attendance is within acceptable range
            if (attendancesInt[i] > totalHoursInt[i]) {
                output.put("error", true);
                output.put("message", "Attendance hours cannot exceed total assigned hours.");
                return output;
            }
        }

        output.put("attendance_int", attendancesInt);
        output.put("total_hours_int", totalHoursInt);

        return output;
    }

    public static int getEngagementScore(int[] attendances, int[] totalHours) {
        double[] weights = {0.3, 0.4, 0.15, 0.15};
        double scoreDouble = 0;
        for (int i = 0; i < attendances.length; i++) {
            if (totalHours[i] != 0) {
                scoreDouble += ((double) attendances[i] * weights[i]) / totalHours[i];
            }
            
        }
        return (int)Math.round(scoreDouble * 100);
    }
    
}
