package com.api.ems.checkins;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckinDto {
    private Long id;
    private String attendee;
    private LocalDateTime checkInTime;
    private String scannedBy;
}
