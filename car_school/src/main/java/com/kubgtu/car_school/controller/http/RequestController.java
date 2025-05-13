package com.kubgtu.car_school.controller.http;

import com.kubgtu.car_school.model.DTO.UserRequestDTO;
import com.kubgtu.car_school.model.requests.CreateUserRequestRequest;
import com.kubgtu.car_school.model.requests.UpdateUserRequestRequest;
import com.kubgtu.car_school.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request")
@AllArgsConstructor
@Validated
public class RequestController {

    private final RequestService requestService;

    @Operation(
            summary = "Создать заявку",
            description = "Доступно админам и юзерам"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN') and !hasRole('STUDENT')")
    public ResponseEntity<Void> makeRequest(@Valid  @RequestBody CreateUserRequestRequest createUserRequestRequest) {
        requestService.makeRequest(createUserRequestRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновить информацию о заявке",
            description = "Доступно админам"
    )
    @PatchMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserRequestDTO> updateRequest(@Valid @RequestBody UpdateUserRequestRequest updateUserRequestRequest) {
        return ResponseEntity.ok(requestService.update(updateUserRequestRequest));
    }

    @Operation(
            summary = "Удалить заявку по id",
            description = "Доступно админам"
    )
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRequest(@Valid @RequestParam Long id) {
        requestService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить заявку по id",
            description = "Доступно админам"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserRequestDTO> getRequest(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(requestService.getById(id));
    }

    @Operation(
            summary = "Получить все заявки",
            description = "Доступно админам"
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRequestDTO>> getRequests(@RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "100") int size) {

        return ResponseEntity.ok(requestService.getByPage(page, size));
    }
}