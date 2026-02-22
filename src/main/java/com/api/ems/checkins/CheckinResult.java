package com.api.ems.checkins;

import lombok.Data;

@Data
public class CheckinResult {
    private Long id;
    private Long attendee;
    private Long scannedBy;
}
