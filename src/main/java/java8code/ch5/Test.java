package java8code.ch5;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by ftsan on 2015/11/23.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(300);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(timeElapsed.toNanos());
        System.out.println(timeElapsed.getNano());
        System.out.println(timeElapsed.toMillis());
        System.out.println(timeElapsed.getSeconds());

        System.out.println(timeElapsed.plus(timeElapsed).toMillis());
        System.out.println(timeElapsed.plusMillis(200l).toMillis());

        Duration negate = Duration.between(end, start);
        System.out.println(negate.toMillis());
        System.out.println(negate.isNegative());
        System.out.println(Duration.ZERO.isZero());

        Instant start2 = Instant.now();
        Thread.sleep(1000);
        Instant end2 = Instant.now();
        Duration timeElapsed2 = Duration.between(start2, end2);
        System.out.println(timeElapsed2.toNanos());
        System.out.println(timeElapsed.multipliedBy(10).minus(timeElapsed2).isNegative());

        System.out.println(LocalDate.now());
        System.out.println(LocalDate.of(1903, 6, 14));
        System.out.println(LocalDate.of(1903, Month.JUNE, 14));

        LocalDate now = LocalDate.now();
        System.out.println(now.withYear(2011));
        System.out.println(now.isBefore(LocalDate.of(2014, Month.DECEMBER, 31)));
        System.out.println(now.isAfter(LocalDate.of(2014, Month.DECEMBER, 31)));
        System.out.println(LocalDate.of(2000, 1, 1).isLeapYear());

        // Programmer's day
        System.out.println(LocalDate.of(2015, 1, 1).plusDays(255));

        LocalDate independenceDay = LocalDate.of(2015, Month.JULY, 4);
        LocalDate christmas = LocalDate.of(2015, Month.DECEMBER, 25);
        System.out.println(independenceDay.until(christmas));
        System.out.println(independenceDay.until(christmas, ChronoUnit.DAYS));
        System.out.println(LocalDate.of(2016, 1, 31).plusMonths(1));

        System.out.println(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)));
        System.out.println(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
        System.out.println(LocalDate.now().with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY)));
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY)));
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()));

//        TemporalAdjuster NEXT_WORKDAY = w -> {
//            LocalDate result = (LocalDate) w;
//            do {
//                result = result.plusDays(1);
//            } while (result.getDayOfWeek().getValue() >= 6);
//            return result;
//        };

        // キャスト不要
        TemporalAdjuster NEXT_WORKDAY =
                TemporalAdjusters.ofDateAdjuster(w -> {
                    LocalDate result = w;
                    do {
                        result = result.plusDays(1);
                    } while (result.getDayOfWeek().getValue() >= 6);
                    return result;
                });

        System.out.println(now.with(NEXT_WORKDAY));

        LocalTime rightNow = LocalTime.now();
        LocalTime bedtime = LocalTime.of(22, 30);
        bedtime = LocalTime.of(22, 30, 0);
        System.out.println(bedtime.plusHours(8));
        System.out.println(bedtime.toSecondOfDay());
        ZonedDateTime apollo11launch = ZonedDateTime.of(1969, 7, 16, 9, 32, 0, 0, ZoneId.of("America/New_York"));
        System.out.println(apollo11launch);
        System.out.println(apollo11launch.toInstant());
        System.out.println(apollo11launch.toInstant().atZone(ZoneId.of("UTC")));
        ZonedDateTime skipped = ZonedDateTime.of(
                LocalDate.of(2013, 3, 31),     // サマータイムの終了
                LocalTime.of(2, 30),
                ZoneId.of("Europe/Berlin"));
        System.out.println(skipped);
        ZonedDateTime ambigous = ZonedDateTime.of(
                LocalDate.of(2013, 10, 27),     // サマータイムの終了
                LocalTime.of(2, 30),
                ZoneId.of("Europe/Berlin"));
        System.out.println(ambigous);
        ZonedDateTime anHourLater = ambigous.plusHours(1);
        System.out.println(anHourLater);

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        String formatted = formatter.format(apollo11launch);
        System.out.println(formatted);
        System.out.println(formatter.withLocale(Locale.JAPAN).format(apollo11launch));
        System.out.println(DateTimeFormatter.ofPattern("yyyy/MM/dd").format(apollo11launch));
        System.out.println(LocalDate.parse("1903-06-14").getClass().getName());
        System.out.println(LocalDate.parse("1969-07-16 03:32:00-0400", DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ssxx")).getClass().getName());
        System.out.println(Date.from(Instant.now()));
        System.out.println(GregorianCalendar.from(apollo11launch));
    }
}
