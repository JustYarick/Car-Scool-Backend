package com.kubgtu.car_school.controller.http;

import com.kubgtu.car_school.model.DTO.ScheduleDTO;
import com.kubgtu.car_school.model.requests.CreateScheduleRequest;
import com.kubgtu.car_school.model.requests.UpdateScheduleRequest;
import com.kubgtu.car_school.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
@Validated
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(
            summary = "Получить расписание по id",
            description = "Доступно всем"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @Operation(
            summary = "Получить все расписание",
            description = "Доступно всем"
    )
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules(@RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false, defaultValue = "100") int size) {
        return ResponseEntity.ok(scheduleService.getByPage(page, size));
    }

    @Operation(
            summary = "Cоздать расписание",
            description = "Доступно админам, преподавателям"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<ScheduleDTO> createSchedule(@Valid @RequestBody CreateScheduleRequest scheduleRequest) {
        return ResponseEntity.ok(scheduleService.create(scheduleRequest));
    }

    @Operation(
            summary = "Обновить расписание по id",
            description = "Доступно админам, преподавателям"
    )
    @PatchMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<ScheduleDTO> updateSchedule(@Valid @RequestBody UpdateScheduleRequest updateScheduleRequest) {
        return ResponseEntity.ok(scheduleService.update(updateScheduleRequest));
    }

    @Operation(
            summary = "Удалить расписание по id",
            description = "Доступно админам"
    )
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<Void> deleteSchedule(@Valid @RequestParam Long id) {
        scheduleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
