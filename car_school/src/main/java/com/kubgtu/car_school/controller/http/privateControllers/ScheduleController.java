package com.kubgtu.car_school.controller.http.privateApi;

import com.kubgtu.car_school.model.DTO.ScheduleDTO;
import com.kubgtu.car_school.model.requests.ScheduleRequest;
import com.kubgtu.car_school.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllLessons());
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok(scheduleService.create(scheduleRequest));
    }
}
