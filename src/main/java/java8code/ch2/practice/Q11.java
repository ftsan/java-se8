package java8code.ch2.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by futeshi on 2015/10/26.
 */
public class Q11 {
    public static void main(String[] args) {
        Stream<Integer> stream = IntStream.range(0, 1000).boxed();
        List<Integer> l = new ArrayList<Integer>() {
            {
                for (int i = 0; i < 1000; i++) {
                    add(1);
                }
            }
        };

        synchronized (l) {
            stream.parallel().forEach(i -> l.set(i, i));
        }
        System.out.println(l.size());
    }
}
