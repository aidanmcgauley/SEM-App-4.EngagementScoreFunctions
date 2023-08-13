package com.qub.engagementscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class Application {

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

        String[] items = {item_1, item_2, item_3, item_4};
        int[] attendances = {
                Integer.parseInt(attendance_1),
                Integer.parseInt(attendance_2),
                Integer.parseInt(attendance_3),
                Integer.parseInt(attendance_4)
        };
        int[] totalHours = {
                Integer.parseInt(total_hours_1),
                Integer.parseInt(total_hours_2),
                Integer.parseInt(total_hours_3),
                Integer.parseInt(total_hours_4)
        };

        // First, call parameterChecker to validate the inputs
        Map<String, Object> parameterCheckOutput = Functions.parameterChecker(items, attendances, totalHours);

        // If no error found, call getEngagementScore and proceed
        if (!(boolean) parameterCheckOutput.get("error")) {
            int engagementScore = Functions.getEngagementScore(attendances, totalHours);
            response.put("error", false);
            response.put("engagement_score", engagementScore);
        } else {
            // If there was an error in the parameter check, you can handle it here
            response.put("error", true);
            response.put("message", parameterCheckOutput.get("message"));
        }

        return response;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
