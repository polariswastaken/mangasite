package dev.polar.reader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController tells Spring: "This class handles web requests."
// It creates an instance of this object automatically (This is called a "Bean").
@RestController
public class HealthController {

    // @GetMapping tells Spring: "When someone visits /status, run this method."
    @GetMapping("/status")
    public String checkSystemStatus() {
        return "System is Online. Operational level: 9000.";
    }
}