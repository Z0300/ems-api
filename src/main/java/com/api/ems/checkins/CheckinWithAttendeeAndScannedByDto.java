package com.api.ems.checkins;

import com.api.ems.users.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckinWithAttendeeAndScannedByDto {
    private Long id;
    private UserDto attendee;
    private LocalDateTime checkInTime;
    private UserDto scannedBy;
}
