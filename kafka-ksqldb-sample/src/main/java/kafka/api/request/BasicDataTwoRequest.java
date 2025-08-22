package kafka.api.request;

import lombok.Data;

@Data
public class BasicDataTwoRequest {

    private Date myDate;
    private Time myTime;
    private Timestamp myTimestamp;

    @Data
    public static class Date{

        private int year;
        private int month;
        private int date;

    }

    @Data
    public static class Time{

        private int hour;
        private int minute;
        private int second;

    }

    @Data
    public static class Timestamp{

        private int year;
        private int month;
        private int date;
        private int hour;
        private int minute;
        private int second;

    }
}
