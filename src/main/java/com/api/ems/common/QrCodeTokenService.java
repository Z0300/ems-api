package com.api.ems.common;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Service
public class QrCodeTokenService {

    private final QrCodeConfig qrConfig;

    public String generateToken(
            Long eventId,
            LocalDate eventDate,
            LocalTime startTime,
            LocalTime endTime) {

        LocalDateTime eventStart = LocalDateTime.of(eventDate, startTime);
        LocalDateTime validFrom = eventStart.minusHours(qrConfig.getExpirationInHours());
        LocalDateTime validUntil = LocalDateTime.of(eventDate, endTime);

        return Jwts
                .builder()
                .subject("ems_qr")
                .claim("qr", UUID.randomUUID())
                .claim("eid", eventId)
                .issuedAt(Date.from(validFrom.atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(validUntil.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(qrConfig.getSecretKey())
                .compact();
    }

}
