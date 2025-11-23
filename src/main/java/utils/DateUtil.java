package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    private static final DateTimeFormatter DISPLAY_TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "";
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : "";
    }

    public static String formatDisplayDate(LocalDate date) {
        return date != null ? date.format(DISPLAY_DATE_FORMATTER) : "";
    }

    public static String formatDisplayTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DISPLAY_TIME_FORMATTER) : "";
    }

    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    }

    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    public static boolean isDateInFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    public static boolean isValidDateRange(LocalDate start, LocalDate end) {
        return start != null && end != null && !start.isAfter(end);
    }
}