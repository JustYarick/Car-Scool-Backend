package com.kubgtu.car_school.controller.http.privateControllers;

import com.kubgtu.car_school.model.DTO.ScheduleDTO;
import com.kubgtu.car_school.model.requests.CreateScheduleRequest;
import com.kubgtu.car_school.model.requests.UpdateScheduleRequest;
import com.kubgtu.car_school.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(
            summary = "Получить расписание по id",
            description = "Доступно всем"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }


    @Operation(
            summary = "Получить все расписание",
            description = "Доступно всем"
    )
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllLessons());
    }

    @Operation(
            summary = "Получить создать расписание",
            description = "Доступно админам, преподавателям"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody CreateScheduleRequest scheduleRequest) {
        return ResponseEntity.ok(scheduleService.create(scheduleRequest));
    }

    @Operation(
            summary = "Обнаваить расписание по id",
            description = "Доступно админам, преподавателям"
    )
    @PatchMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<ScheduleDTO> updateSchedule(@RequestBody UpdateScheduleRequest updateScheduleRequest) {
        return ResponseEntity.ok(scheduleService.update(updateScheduleRequest));
    }

    @Operation(
            summary = "Удалить расписание по id",
            description = "Доступно админам"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
