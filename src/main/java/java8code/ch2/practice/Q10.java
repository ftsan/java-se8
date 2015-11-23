package java8code.ch2.practice;

import java.util.stream.Stream;

/**
 * Created by ftsan on 2015/10/25.
 */
public class Q10 {
    public static double avg(Stream<Double> stream) {
        Calculator calculator = new Calculator();
        return stream.reduce(0.0, (total, d) -> calculator.avg(d), (total1, total2) -> total1 + total2);
    }

    public static void main(String[] args) {
        System.out.println(avg(Stream.iterate(0.0, d -> d + 1).limit(100)));
        System.out.println(Stream.iterate(0.0, d -> d + 1).limit(100).mapToDouble(d -> d).average());
    }

    static class Calculator {
        private int count = 0;
        private double total = 0.0;

        public double avg(double d) {
            count++;
            total += d;
            return total / count;
        }
    }
}
