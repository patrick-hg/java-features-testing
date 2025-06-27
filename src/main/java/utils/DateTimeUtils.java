package utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static LocalDateTime localDateTimeFromTimestamp(long timestamp, ZoneId zoneId) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime creationDateTime = LocalDateTime.ofInstant(instant, zoneId != null ? zoneId : ZoneId.of("Europe/Paris"));
        return creationDateTime;
    }

    public static String formattedDateTimeFromTimestamp(long timestamp, ZoneId zoneId, DateTimeFormatter formatter) {
        return localDateTimeFromTimestamp(timestamp, zoneId).format(formatter);
    }
}
