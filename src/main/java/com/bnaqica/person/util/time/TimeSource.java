package com.bnaqica.person.util.time;

import java.time.*;

public interface TimeSource {
    public LocalDateTime now();
    public LocalDate today();
    public ZoneId getTimeZone();
}
