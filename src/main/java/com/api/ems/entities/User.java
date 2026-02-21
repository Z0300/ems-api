package com.api.ems.entities;

import com.api.ems.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "scannedBy")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "organizer")
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "attendee")
    private List<Registration> registrations = new ArrayList<>();


}