package com.kubgtu.car_school.service_tests;

import com.kubgtu.car_school.exception.ExceptionClass.ResourceNotFoundException;
import com.kubgtu.car_school.model.DTO.UserRequestDTO;
import com.kubgtu.car_school.model.RequestStatusTypes;
import com.kubgtu.car_school.model.entity.UserRequestEntity;
import com.kubgtu.car_school.model.interfaces.IamApiService;
import com.kubgtu.car_school.model.requests.CreateUserRequestRequest;
import com.kubgtu.car_school.model.requests.UpdateUserRequestRequest;
import com.kubgtu.car_school.repository.UserRequestRepository;
import com.kubgtu.car_school.service.RequestService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;

import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;


import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @InjectMocks
    private RequestService requestService;

    @Mock
    private UserRequestRepository userRequestRepository;

    @Mock
    private IamApiService identityService;

    @Mock
    private Jwt jwt;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private final UUID userId = UUID.randomUUID();

    @Test
    void makeRequest_shouldCreateOrUpdateRequest() {
        CreateUserRequestRequest request = new CreateUserRequestRequest();
        request.setTelephone("123456789");
        request.setTimeToCall(LocalDateTime.of(2020, 1, 1, 0, 0));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaim("sub")).thenReturn(userId.toString());
        SecurityContextHolder.setContext(securityContext);

        when(userRequestRepository.findByUserId(userId)).thenReturn(List.of());

        requestService.makeRequest(request);

        verify(userRequestRepository).save(argThat(entity ->
                entity.getUserId().equals(userId) &&
                        entity.getTelephone().equals("123456789") &&
                        entity.getTimeToCall().equals(LocalDateTime.of(2020, 1, 1, 0, 0))
        ));
    }

    @Test
    void getByPage_shouldReturnPagedRequests() {
        UserRequestEntity entity = new UserRequestEntity();
        entity.setUserId(userId);
        entity.setTelephone("123");
        entity.setTimeToCall(LocalDateTime.of(2020, 1, 1, 0, 0));

        when(userRequestRepository.findAllByOrderByCreateRequestDateAsc(PageRequest.of(0, 10)))
                .thenReturn(List.of(entity));

        UserRepresentation mockUser = new UserRepresentation();
        mockUser.setId(String.valueOf(userId));
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");

        when(identityService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        List<UserRequestDTO> result = requestService.getByPage(0, 10);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTelephone()).isEqualTo("123");
    }

    @Test
    void update_shouldUpdateAndReturnDTO() {
        UpdateUserRequestRequest update = new UpdateUserRequestRequest();
        update.setId(1L);
        update.setUserId(userId);
        update.setTelephone("999");
        update.setTimeToCall(LocalDateTime.of(2020, 1, 1, 0, 0));
        update.setStatus(RequestStatusTypes.NEW);

        UserRequestEntity entity = new UserRequestEntity();
        entity.setId(1L);

        UserRepresentation mockUser = new UserRepresentation();
        mockUser.setId(String.valueOf(userId));
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");

        when(userRequestRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userRequestRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(identityService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        UserRequestDTO result = requestService.update(update);

        assertThat(result.getTelephone()).isEqualTo("999");
        verify(userRequestRepository).save(any());
    }

    @Test
    void delete_shouldRemoveRequestById() {
        requestService.delete(1L);
        verify(userRequestRepository).deleteById(1L);
    }

    @Test
    void getById_shouldReturnDTO() {
        UserRequestEntity entity = new UserRequestEntity();
        entity.setId(1L);
        entity.setUserId(userId);
        entity.setTelephone("555");

        UserRepresentation mockUser = new UserRepresentation();
        mockUser.setId(String.valueOf(userId));
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");

        when(userRequestRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(identityService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        UserRequestDTO dto = requestService.getById(1L);

        assertThat(dto.getTelephone()).isEqualTo("555");
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        when(userRequestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> requestService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("request not found");
    }
}
