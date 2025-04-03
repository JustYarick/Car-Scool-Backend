package com.kubgtu.car_school.controller.http.publicControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
