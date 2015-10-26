package java8code.ch2.practice;

import java.util.stream.Stream;

/**
 * Created by futeshi on 2015/10/24.
 */
public class Q2 {
    public static void main(String[] args) {
        Stream.iterate("", s -> s + "a").limit(20).filter(s -> {
            System.out.println(s);
            return s.length() > 5;
        }).limit(5).count();
    }
}
