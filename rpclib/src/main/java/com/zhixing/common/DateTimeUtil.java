package com.zhixing.common;

public class DateTimeUtil {
    public static void main(String[] args) {
        System.out.println(minConvertDayHourMin(1000.0));
    }
    /**
     * @param min
     * @return day hour min
     */
    public static String minConvertDayHourMin(Double min) {
        String html = "0分";
        if (min != null) {
            Double m = (Double) min;
            String format;
            Object[] array;
            Integer days = (int) (m / (60 * 24));
            Integer hours = (int) (m / (60) - days * 24);
            Integer minutes = (int) (m - hours * 60 - days * 24 * 60);
            if (days > 0) {
                format = "%1$,d天%2$,d时%3$,d分";
                array = new Object[]{days, hours, minutes};
            } else if (hours > 0) {
                format = "%1$,d时%2$,d分";
                array = new Object[]{hours, minutes};
            } else {
                format = "%1$,d分";
                array = new Object[]{minutes};
            }
            html = String.format(format, array);
        }
        return html;
    }

    /**
     * @param day
     * @param hour
     * @param min
     * @return min
     */
    public static int dayHourMinConvertMin(int day, int hour, int min) {
        int days = day * 24 * 60;
        int hours = hour * 60;
        return days + hours + min;
    }
}
