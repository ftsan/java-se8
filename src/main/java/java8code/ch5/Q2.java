package java8code.ch5;

import java.time.LocalDate;

/**
 * Created by ftsan on 2015/11/23.
 */
public class Q2 {
    public static void main(String[] args) {
        System.out.println(LocalDate.of(2000, 2, 29).plusYears(1));
        System.out.println(LocalDate.of(2000, 2, 29).plusYears(4));
        System.out.println(LocalDate.of(2000, 2, 29)
                .plusYears(1)
                .plusYears(1)
                .plusYears(1)
                .plusYears(1));
    }
}
