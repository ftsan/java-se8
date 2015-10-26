package java8code.ch2.practice;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by futeshi on 2015/10/25.
 */
public class Q8 {
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        List<T> l1 = first.collect(Collectors.toList());
        List<T> l2 = second.collect(Collectors.toList());
        int limit = Math.min(l1.size(), l2.size());
        Stream.Builder<T> builder = Stream.builder();
        for(int i = 0; i < limit; i++) {
            builder.add(l1.get(i));
            builder.add(l2.get(i));
        }
        return builder.build();
    }

    public static void main(String[] args) {
        System.out.println(
                zip(Stream.iterate(0, i -> i + 1).limit(10), Stream.iterate(0, i -> i + 1)
                        .limit(10)).collect(Collectors.toList()));
    }
}
