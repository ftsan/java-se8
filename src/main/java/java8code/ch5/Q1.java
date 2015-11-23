package java8code.ch5;

import java.time.LocalDate;

/**
 * Created by ftsan on 2015/11/23.
 */
public class Q1 {
    public static void main(String[] args) {
        System.out.println(LocalDate.of(2015, 1, 1).withDayOfYear(256));
    }
}
