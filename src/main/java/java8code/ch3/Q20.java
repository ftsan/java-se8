package java8code.ch3;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by futeshi on 2015/11/03.
 */
public class Q20 {
    public static void main(String[] args) {
        List<Integer> l = IntStream.iterate(0, i -> i + 1).boxed().limit(20).collect(Collectors.toList());
        System.out.println(map(l, i -> i * 2));
    }

    public static <T, U> List<U> map(List<T> l, Function<T, U> f) {
        return l.stream().map(f).collect(Collectors.toList());
    }
}
