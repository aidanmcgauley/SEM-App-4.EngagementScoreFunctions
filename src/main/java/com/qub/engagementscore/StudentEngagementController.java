package com.qub.engagementscore;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class StudentEngagementController {

    @GetMapping("/")
    public Map<String, Object> calculateStudentEngagementScore(
        
        @RequestParam String item_1,
        @RequestParam String item_2,
        @RequestParam String item_3,
        @RequestParam String item_4,
        @RequestParam String attendance_1,
        @RequestParam String attendance_2,
        @RequestParam String attendance_3,
        @RequestParam String attendance_4,
        @RequestParam String total_hours_1,
        @RequestParam String total_hours_2,
        @RequestParam String total_hours_3,
        @RequestParam String total_hours_4) {

        Map<String, Object> response = new HashMap<>();

        int[] attendances = new int[4];
        int[] totalHours = new int[4];
        String[] attendanceStrings = {attendance_1, attendance_2, attendance_3, attendance_4};
        String[] totalHoursStrings = {total_hours_1, total_hours_2, total_hours_3, total_hours_4};

        // define weights
        double[] weights = {0.3, 0.4, 0.15, 0.15};

        for (int i = 0; i < attendanceStrings.length; i++) {
            try {
                attendances[i] = Integer.parseInt(attendanceStrings[i]);
                totalHours[i] = Integer.parseInt(totalHoursStrings[i]);
            } catch (NumberFormatException e) {
                response.put("error", true);
                response.put("message", "Attendance and total hours must be integers.");
                return response;
            }

            // Check if attendance is non-negative
            if (attendances[i] < 0 || totalHours[i] < 0) {
                response.put("error", true);
                response.put("message", "Invalid attendance or total hours - must be a positive integer");
                return response;
            }

            if (attendances[i] > totalHours[i]) {
                response.put("error", true);
                response.put("message", "Attendance cannot be greater than the total available hours");
                return response;
            }

        }

        // calculate the engagement score
        double scoreDouble = 0;
        for (int i = 0; i < attendances.length; i++) {
            scoreDouble += ((double) attendances[i] * weights[i]) / totalHours[i];
        }

        int score = (int)Math.round(scoreDouble * 100);
        response.put("engagement_score", score);

        return response;
    }

}