package com.kubgtu.car_school.controller.http.publicApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
