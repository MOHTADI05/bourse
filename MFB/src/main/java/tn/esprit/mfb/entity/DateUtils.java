package tn.esprit.mfb.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final Locale DEFAULT_LOCALE = Locale.US; // Change this as needed

    public static Date getStartOfToday() {
        Calendar calendar = Calendar.getInstance(DEFAULT_LOCALE);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getStartOfWeek() {
        Calendar calendar = Calendar.getInstance(DEFAULT_LOCALE);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getStartOfYear() {
        Calendar calendar = Calendar.getInstance(DEFAULT_LOCALE);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}