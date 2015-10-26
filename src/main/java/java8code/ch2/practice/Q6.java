package java8code.ch2.practice;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by futeshi on 2015/10/25.
 */
public class Q6 {
    public static Stream<Character> characterStream(String s) {
        return IntStream.range(0, s.length()).boxed().map(s::charAt);
    }

    public static void main(String[] args) {
        characterStream("abcde").forEach(System.out::println);
    }
}
