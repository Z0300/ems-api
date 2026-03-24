package com.api.ems.users;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardDto {
    private List<UserEventDto> recommended;
    private List<UserEventDto> popular;
    private List<UserEventDto> registered;
    private List<UserEventDto> past;
}
