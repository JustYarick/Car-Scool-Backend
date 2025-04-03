package com.kubgtu.car_school.service;

import com.kubgtu.car_school.entity.UserRequestEntity;
import com.kubgtu.car_school.exception.ExceptionClass.ResourceNotFoundException;
import com.kubgtu.car_school.model.DTO.UserRequestDTO;
import com.kubgtu.car_school.model.requests.CreateUserRequestRequest;
import com.kubgtu.car_school.model.requests.UpdateUserRequestRequest;
import com.kubgtu.car_school.repository.UserRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RequestService {

    private final UserRequestRepository userRequestRepository;
    private final SecurityContextService securityContextService;
    private final KeycloakUserService keycloakUserService;

    public void makeRequest(CreateUserRequestRequest createUserRequestRequest) {

        UUID userUuid = securityContextService.getCurrentUserUuid();
        Optional<UserRequestEntity> existingRequest = userRequestRepository.findByUserId(userUuid).stream().findFirst();
        UserRequestEntity requestEntity = existingRequest.orElse(new UserRequestEntity());

        requestEntity.setTelephone(createUserRequestRequest.getTelephone());
        requestEntity.setUserId(userUuid);
        requestEntity.setTimeToCall(createUserRequestRequest.getTimeToCall());

        userRequestRepository.save(requestEntity);
    }


    public List<UserRequestDTO> getByCount(int countStart, int countEnd) {
        return userRequestRepository.findAllByOrderByCreateRequestDateAsc(PageRequest.of(countStart, countEnd))
                .stream()
                .map(request -> UserRequestDTO.convert(request, keycloakUserService))
                .toList();
    }

    public List<UserRequestDTO> getAll() {
        return userRequestRepository.findAll()
                .stream()
                .map(request -> UserRequestDTO.convert(request, keycloakUserService))
                .toList();
    }

    public UserRequestDTO update(UpdateUserRequestRequest updateUserRequestRequest) {
        UserRequestEntity request = userRequestRepository.findById(updateUserRequestRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Request not found"));

        request.setTelephone(updateUserRequestRequest.getTelephone());
        request.setUserId(updateUserRequestRequest.getUserId());
        request.setTimeToCall(updateUserRequestRequest.getTimeToCall());
        request.setStatus(updateUserRequestRequest.getStatus());

        userRequestRepository.save(request);
        return UserRequestDTO.convert(request, keycloakUserService);
    }

    public void delete(Long id) {
        userRequestRepository.deleteById(id);
    }

    public UserRequestDTO getById(Long id) {
        UserRequestEntity request = userRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("request not found"));
        return UserRequestDTO.convert(request, keycloakUserService);
    }
}