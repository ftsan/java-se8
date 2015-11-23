package java8code.ch5;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Predicate;

/**
 * Created by ftsan on 2015/11/23.
 */
public class Q3 {
    public static TemporalAdjuster next(Predicate<LocalDate> term) {
        return TemporalAdjusters.ofDateAdjuster(w -> {
            LocalDate result = w.plusDays(1);
            while (!term.test(result)) {
                result = result.plusDays(1);
            }
            return result;
        });
    }
}
