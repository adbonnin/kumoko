package fr.adbonnin.kumoko.time;

public class DurationUtils {

    public static final long MILLIS_PER_SECOND = 1000;

    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;

    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    public static long ofSeconds(long seconds) {
        return ofSeconds(seconds, 0);
    }

    public static long ofSeconds(long seconds, long milliseconds) {
        return ofMinutes(0, seconds, milliseconds);
    }

    public static long ofMinutes(long minutes) {
        return ofMinutes(minutes, 0, 0);
    }

    public static long ofMinutes(long minutes, long seconds) {
        return ofMinutes(minutes, seconds, 0);
    }

    public static long ofMinutes(long minutes, long seconds, long milliseconds) {
        return ofHours(0, minutes, seconds, milliseconds);
    }

    public static long ofHours(long hours) {
        return ofHours(hours, 0, 0, 0);
    }

    public static long ofHours(long hours, long minutes) {
        return ofHours(hours, minutes, 0, 0);
    }

    public static long ofHours(long hours, long minutes, long seconds) {
        return ofHours(hours, minutes, seconds, 0);
    }

    public static long ofHours(long hours, long minutes, long seconds, long milliseconds) {
        return ofDays(0, hours, minutes, seconds, milliseconds);
    }

    public static long ofDays(long days) {
        return ofDays(days, 0, 0, 0, 0);
    }

    public static long ofDays(long days, long hours) {
        return ofDays(days, hours, 0, 0, 0);
    }

    public static long ofDays(long days, long hours, long minutes) {
        return ofDays(days, hours, minutes, 0, 0);
    }

    public static long ofDays(long days, long hours, long minutes, long seconds) {
        return ofDays(days, hours, minutes, seconds, 0);
    }

    public static long ofDays(long days, long hours, long minutes, long seconds, long milliseconds) {
        return days * MILLIS_PER_DAY +
                hours * MILLIS_PER_HOUR +
                minutes * MILLIS_PER_MINUTE +
                seconds * MILLIS_PER_SECOND +
                milliseconds;
    }

    private DurationUtils() { /* Cannot be instantiated */ }
}
