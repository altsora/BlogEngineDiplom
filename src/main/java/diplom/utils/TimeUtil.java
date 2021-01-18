package diplom.utils;

import java.time.*;

public final class TimeUtil {
    public static final ZoneId TIME_ZONE = ZoneId.of("UTC");
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public static long getTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZONE_OFFSET).getEpochSecond();
    }

    public static LocalDateTime getLocalDateTimeFromTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TIME_ZONE);
    }

    @Deprecated
    public static void returnToPresentIfOld(LocalDateTime localDateTime) {
        if (localDateTime.isBefore(LocalDateTime.now(TIME_ZONE))) {
            localDateTime = LocalDateTime.now(TIME_ZONE);
        }
    }

    @Deprecated
    public static LocalDateTime convertLocalTimeInUtcTime(LocalDateTime localDateTime) {
        ZonedDateTime localZone = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZone = localZone.withZoneSameInstant(TIME_ZONE);
        return utcZone.toLocalDateTime();
    }

    private TimeUtil() {
    }
}
