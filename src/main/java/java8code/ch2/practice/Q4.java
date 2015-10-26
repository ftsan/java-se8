package java8code.ch2.practice;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by futeshi on 2015/10/24.
 */
public class Q4 {
    public static void main(String[] args) {
        int[] values = {1, 3, 9, 16};
        Stream<int[]> s = Stream.of(values);
        IntStream.of(values);
    }
}
