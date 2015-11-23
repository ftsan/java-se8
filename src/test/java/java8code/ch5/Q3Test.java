package java8code.ch5;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static java8code.ch5.Q3.*;

/**
 * Created by ftsan on 2015/11/23.
 */
public class Q3Test {
    Predicate<LocalDate> term;

    @Before
    public void setUpTermMoreAfterTodayAndWeekday() {
        term = w -> w.getDayOfWeek().getValue() < 6;
    }

    @Test
    public void checkWeekday() {
        LocalDate localDate = LocalDate.of(2015, 11, 23);
        assertTrue(localDate.with(next(term)).compareTo(LocalDate.of(2015, 11, 24)) == 0);
    }

    @Test
    public void checkFriday() {
        LocalDate friday = LocalDate.of(2015, 11, 27);
        assertThat(friday.with(next(term)), is(comparesEqualTo(LocalDate.of(2015, 11, 30))));
    }

    @Test
    public void checkSaturday() {
        LocalDate saturday = LocalDate.of(2015, 11, 28);
        assertThat(saturday.with(next(term)), is(comparesEqualTo(LocalDate.of(2015, 11, 30))));
    }
}
