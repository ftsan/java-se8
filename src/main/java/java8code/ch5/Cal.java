package java8code.ch5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by ftsan on 2015/11/23.
 */
public class Cal {
    /**
     * How to call:
     *    java Cal ${month} ${year}
     * @param args[0] month, args[1] year
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new RuntimeException("Please specify two args.");
        }

        int month = Integer.parseInt(args[0]);
        int year = Integer.parseInt(args[1]);

        LocalDate today = LocalDate.of(year, month, 1);
        LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
        DayOfWeek week = DayOfWeek.MONDAY;
        StringBuilder cal = new StringBuilder();
        while (today.compareTo(lastDay) < 0) {
            while (today.getDayOfMonth() == 1 && today.getDayOfWeek().compareTo(week) > 0) {
                cal.append("   ");
                week = week.plus(1);
            }

            while (true) {
                cal.append(String.format("%2d", today.getDayOfMonth()));
                if (today.compareTo(lastDay) >= 0) {
                    break;
                }
                if (today.getDayOfWeek().compareTo(DayOfWeek.SUNDAY) != 0) {
                    cal.append(" ");
                    today = today.plusDays(1);
                } else {
                    cal.append("\n");
                    break;
                }
            }
            today = today.plusDays(1);
        }
        System.out.println(cal);
    }
}
