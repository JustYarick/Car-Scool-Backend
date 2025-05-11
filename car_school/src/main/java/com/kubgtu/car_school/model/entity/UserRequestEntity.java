package com.kubgtu.car_school.model.entity;

import com.kubgtu.car_school.model.RequestStatusTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userId;
    private String telephone;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createRequestDate;
    private LocalDateTime timeToCall;
    private RequestStatusTypes status = RequestStatusTypes.NEW;
}