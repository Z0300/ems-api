package com.api.ems.checkins;

import com.api.ems.common.ErrorDto;
import com.api.ems.common.PageDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/api/checkins")
public class CheckinController {

    private final CheckinService checkinService;

    @GetMapping
    public PageDto<CheckinDto> getCheckins(
            @RequestParam(required = false) String name,
            @SortDefault(sort = "checkInTime", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        return checkinService.getCheckins(pageable, name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckinWithAttendeeAndScannedByDto> getCheckin(@PathVariable Long id) {
        var checkin = checkinService.getCheckin(id);
        return ResponseEntity.ok(checkin);
    }

    @PostMapping
    public ResponseEntity<CheckinResult> checkin(
            @Valid @RequestBody CheckinRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var checkinResult = checkinService.checkin(request);
        var uri = uriBuilder.path("/api/checkins/{id}").buildAndExpand(checkinResult.getId()).toUri();
        return ResponseEntity.created(uri).body(checkinResult);
    }

    @ExceptionHandler(CheckinConflictException.class)
    public ResponseEntity<ErrorDto> handleCheckinConflict(CheckinConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorDto(ex.getMessage())
        );
    }

}
