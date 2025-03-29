package com.kubgtu.car_school.controller.http.privateApi;


import com.kubgtu.car_school.model.DTO.StudentDTO;
import com.kubgtu.car_school.service.KeycloakUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    KeycloakUserService keycloakUserService;

    @GetMapping("/test")
    public ResponseEntity<List<StudentDTO>> test() {
        return ResponseEntity.ok(keycloakUserService.getAllStudents());
    }
}
