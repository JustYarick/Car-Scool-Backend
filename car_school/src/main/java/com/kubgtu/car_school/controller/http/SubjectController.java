package com.kubgtu.car_school.controller.http;

import com.kubgtu.car_school.model.requests.CreateSubjectRequest;
import com.kubgtu.car_school.model.requests.UpdateSubjectRequest;
import com.kubgtu.car_school.model.responces.SubjectResponse;
import com.kubgtu.car_school.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subject")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @Operation(
            summary = "Создать предмет",
            description = "Доступно админам"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<SubjectResponse> addSubject(@Valid  @RequestBody CreateSubjectRequest subjectRequest) {
        return ResponseEntity.ok(new SubjectResponse(subjectService.create(subjectRequest)));
    }

    @Operation(
            summary = "Удалить предмет по id",
            description = "Доступно админам"
    )
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSubject(@Valid @RequestParam Long id) {
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить все предметы",
            description = "Доступно всем"
    )
    @GetMapping
    public ResponseEntity<SubjectResponse> getAllSubjects(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "100") int size) {
        return ResponseEntity.ok(SubjectResponse.builder()
                .data(subjectService.getByPage(page, size))
                .build()
        );
    }

    @Operation(
            summary = "Получить предмет по id",
            description = "Доступно всем"
    )
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@Valid @PathVariable  Long id) {
        return ResponseEntity.ok(new SubjectResponse(subjectService.getSubjectById(id)));
    }

    @Operation(
            summary = "Обновить предмет",
            description = "Доступно админам"
    )
    @PatchMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<SubjectResponse> updateSubject(@Valid @RequestBody UpdateSubjectRequest subjectRequest) {
        return ResponseEntity.ok(new SubjectResponse(subjectService.update(subjectRequest)));
    }
}
