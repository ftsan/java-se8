package java8code.ch2.practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ftsan on 2015/10/25.
 */
public class Q9 {
    public static <T> List<T> toFlatList(Stream<List<T>> stream) {
        return stream.reduce((l1, l2) -> {
            // 以下のコードだとUnsupportedOperationException
            // l1.addAll(l2);
            // return l1;
            return new ArrayList<T>() {
                {
                    addAll(l1);
                    addAll(l2);
                }
            };
        }).orElse(Collections.emptyList());
    }

    public static <T> List<T> toFlatList2(Stream<List<T>> stream) {
        return stream.reduce(new ArrayList<>(), (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        });
    }

    public static <T> List<T> toFlatList3(Stream<List<T>> stream) {
        return stream.reduce(new ArrayList<>(), (result, list) -> {
            result.addAll(list);
            return result;
        }, (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        });
    }

    public static int sum(Stream<String> stream) {
        return stream.parallel().reduce(0, (total, str) -> total + str.length(), (total1, total2) -> total1 + total2);
    }

    public static void main(String[] args) {
        System.out.println(toFlatList(Stream.of(Arrays.asList("a", "b", "c"), Arrays.asList("1", "2", "3"))));
        System.out.println(toFlatList2(Stream.of(Arrays.asList("a", "b", "c"), Arrays.asList("1", "2", "3"))));
        System.out.println(toFlatList3(Stream.of(Arrays.asList("a", "b", "c"), Arrays.asList("1", "2", "3"))));
    }
}
