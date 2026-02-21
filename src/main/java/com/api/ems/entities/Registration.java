package com.api.ems.entities;

import com.api.ems.entities.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "registrations")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private User attendee;

    @Column(name = "registration_date", insertable = false, updatable = false)
    private LocalDateTime registrationDate;

    @Column(name = "reference_code")
    private String referenceCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @OneToOne(mappedBy = "registration")
    private Attendance attendance;


}