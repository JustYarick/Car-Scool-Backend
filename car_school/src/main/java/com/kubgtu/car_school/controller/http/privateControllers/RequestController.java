package com.kubgtu.car_school.controller.http.privateControllers;

import com.kubgtu.car_school.model.DTO.UserRequestDTO;
import com.kubgtu.car_school.model.requests.CreateUserRequestRequest;
import com.kubgtu.car_school.model.requests.UpdateUserRequestRequest;
import com.kubgtu.car_school.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request")
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> makeRequest(@RequestBody CreateUserRequestRequest createUserRequestRequest) {
        requestService.makeRequest(createUserRequestRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserRequestDTO> updateRequest(@RequestBody UpdateUserRequestRequest updateUserRequestRequest) {
        return ResponseEntity.ok(requestService.update(updateUserRequestRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRequestDTO> getRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRequestDTO>> getRequests(@RequestParam(required = false) Integer countStart,
                                                            @RequestParam(required = false) Integer countEnd) {

        if (countStart != null && countEnd != null) return ResponseEntity.ok(requestService.getByCount(countStart, countEnd));
        else return ResponseEntity.ok(requestService.getAll());
    }
}