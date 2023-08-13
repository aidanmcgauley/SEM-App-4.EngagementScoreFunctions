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
        String[] attendances = {attendance_1, attendance_2, attendance_3, attendance_4};
        String[] totalHours = {total_hours_1, total_hours_2, total_hours_3, total_hours_4};

        // First, call parameterChecker to validate the inputs
        Map<String, Object> parameterCheckOutput = Functions.parameterChecker(items, attendances, totalHours);

        // If no error found, call getEngagementScore and proceed
        if (!(boolean) parameterCheckOutput.get("error")) {
            int[] attendancesInt = (int[]) parameterCheckOutput.get("attendance_int");
            int[] totalHoursInt = (int[]) parameterCheckOutput.get("total_hours_int");
            int engagementScore = Functions.getEngagementScore(attendancesInt, totalHoursInt);
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
