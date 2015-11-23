package java8code.ch2.practice;

import java.util.DoubleSummaryStatistics;
import java.util.stream.Stream;

/**
 * Created by ftsan on 2015/10/25.
 */
public class Q5 {
    public static Stream<Long> randomStream(long a, int c, int m, long seed) {
        return Stream.iterate(seed, x -> random(a, c, m, x));
    }

    private static long random(long a, int c, int m, long seed) {
        return (a * seed + c) % m;
    }

    public static void main(String[] args) {
        System.out.println(randomStream(25214903917l, 11, (int) Math.pow(2, 48), 11).count());
    }
}
