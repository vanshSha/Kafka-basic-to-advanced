package kafka.api.request;

import lombok.Data;

@Data
public class BasicDataThreeRequest {

    private Date myDate;
    private Time myTime;
    private DateTime myDateTime;
    private DateTimeWithTimeZone myDateTimeWithTimezone;


    @Data
    public static class Date {

        private int year;
        private int month;
        private int date;

    }

    @Data
    public static class DateTime {

        private int year;
        private int month;
        private int date;
        private int hour;
        private int minute;
        private int second;

    }

    @Data
    public static class Time {
        private int hour;
        private int minute;
        private int second;

    }

    @Data
    public static class DateTimeWithTimeZone extends DateTime{
        private String timezone;
    }
}
