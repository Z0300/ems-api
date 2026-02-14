package com.api.ems.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @ManyToOne
    @JoinColumn(name = "scanned_by")
    private User scannedBy;
}