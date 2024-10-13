package commons;

import java.util.Comparator;

public enum WeekDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static final Comparator<WeekDay> comparator = Comparator.comparingInt(Enum::ordinal);

    public static WeekDay[] getWeekDays() {
        return new WeekDay[]{WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY, WeekDay.THURSDAY, WeekDay.FRIDAY};
    }
}
