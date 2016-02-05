package com.bnaqica.person.util.time;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class SystemTimeSource implements TimeSource {
    private static final ZoneId TIME_ZONE = ZoneId.of("UTC");
    
    public SystemTimeSource() {}

    @Override public LocalDateTime now() {
        return LocalDateTime.now(TIME_ZONE);
    }

    @Override public LocalDate today() {
        return LocalDate.now(TIME_ZONE);
    }

    public ZoneId getTimeZone() { return TIME_ZONE; }
}