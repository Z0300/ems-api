package com.api.ems.entities;

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

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "attendee_id")
    private Long attendeeId;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "qr_token")
    private String qrToken;

    @Column(name = "status")
    private String status;

    @OneToOne(mappedBy = "registration")
    private Attendance attendance;


}