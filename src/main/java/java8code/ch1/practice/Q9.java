package java8code.ch1.practice;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by futeshi on 2015/10/22.
 */
public class Q9 {
    interface Collection2<T> extends Collection<T> {
        // fileterした後にforEachするのと比べて、処理を行う回数が減るのがメリット?
        default void forEachIf(Consumer<T> action, Predicate<T> filter) {
            stream().forEach(e -> {
                if (filter.test(e)) {
                    action.accept(e);
                }
            });
        }
    }

    static class ArrayList2<E> extends ArrayList<E> implements Collection2<E> {}

    public static void main(String[] args) {
        ArrayList2<String> list = new ArrayList2<>();
        list.addAll(Arrays.asList("", "a", "bb", "CCC", "dddd"));
        list.forEachIf(System.out::println, e -> e.length() > 2);
    }
}