package diplom.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public final class TimeUtil {
    public static final ZoneId TIME_ZONE = ZoneId.of("UTC");
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public static long getTimestamp(LocalDateTime localDateTime) {
        return localDateTime == null ? 0 : localDateTime.toInstant(ZONE_OFFSET).getEpochSecond();
    }

    public static LocalDateTime getLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TIME_ZONE);
    }

    public static LocalDateTime getLocalDateTimeCheck(long timestamp) {
        LocalDateTime time = getLocalDateTime(timestamp);
        return time.isBefore(now()) ? now() : time;
    }

    private static LocalDateTime now() {
        return LocalDateTime.now();
    }

    private TimeUtil() {
    }
}
