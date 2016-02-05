package com.bnaqica.person.util.time;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    private static final ZoneId UTC = ZoneId.of("UTC");

    public static TemporalUnit timeUnitToTemporalUnit(TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return ChronoUnit.NANOS;
            case MICROSECONDS:
                return ChronoUnit.MICROS;
            case MILLISECONDS:
                return ChronoUnit.MILLIS;
            case SECONDS:
                return ChronoUnit.SECONDS;
            case MINUTES:
                return ChronoUnit.MINUTES;
            case HOURS:
                return ChronoUnit.HOURS;
            case DAYS:
                return ChronoUnit.DAYS;
            default:
                throw new RuntimeException("Error converting TimeUnit " + unit);
        }
    }

    public static Timestamp sqlTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return Timestamp.valueOf(dateTime);
    }

    public static long unixTimestamp(LocalDateTime dateTime) {
        return Instant.from(dateTime.atZone(UTC)).toEpochMilli();
    }
}

