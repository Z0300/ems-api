package com.api.ems.checkins;

import com.api.ems.entities.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckinMapper {
    @Mapping(source = "registration.attendee.fullName", target = "attendee")
    @Mapping(source = "scannedBy.fullName", target = "scannedBy")
    CheckinDto toDto(Attendance attendance);

    @Mapping(source = "registration.attendee", target = "attendee")
    @Mapping(source = "scannedBy", target = "scannedBy")
    CheckinWithAttendeeAndScannedByDto toDetailedDto(Attendance attendance);

    @Mapping(source = "registration.id", target = "attendee")
    @Mapping(source = "scannedBy.id", target = "scannedBy")
    CheckinResult toCheckinResultDto(Attendance attendance);

    Attendance toEntity(CheckinRequest request);
}
