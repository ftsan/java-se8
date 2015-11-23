package java8code.ch2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import static java.util.stream.Collectors.*;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ftsan on 2015/10/23.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Files.lines(FileSystems.getDefault().getPath("", "build.gradle")).forEach(System.out::println);
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
        Files.lines(FileSystems.getDefault().getPath("", "build.gradle")).skip(10).forEach(System.out::println);
        Stream.iterate(1.0, p -> p * 2)
                .peek(e -> System.out.println("Fetching " + e))
                .limit(20).max(Double::compareTo)
                .ifPresent(System.out::println);
        System.out.println(Stream.<String>empty().max(String::compareToIgnoreCase).map(String::length));

        System.out.println(Stream.iterate(1, i -> i + 1).limit(10).reduce((x, y) -> x + y));
        Stream.of("abcde fgh ijk".split("")).reduce((x, y) -> y + x).ifPresent(System.out::println);
        Stream.of(Locale.getAvailableLocales()).limit(5).collect(Collectors.toMap(
                Locale::getDisplayCountry,
                l -> Collections.singleton(l.getDisplayLanguage()),
                (a, b) -> {
                    Set<String> r = new HashSet<>(a);
                    r.addAll(b);
                    return r;
                })).entrySet().forEach(System.out::println);

        Stream.of(Locale.getAvailableLocales())
                .limit(10)
                .collect(Collectors.groupingBy(Locale::getCountry))
                .entrySet()
                .forEach(System.out::println);

        System.out.println(Stream.of(Locale.getAvailableLocales())
                .collect(Collectors.partitioningBy(l -> l.getLanguage().equals("en")))
                .get(true));
        System.out.println(Stream.of(Locale.getAvailableLocales())
                .collect(groupingBy(Locale::getLanguage,
                        summingInt(Locale::hashCode))));
        System.out.println(Stream.of(Locale.getAvailableLocales())
                .collect(groupingBy(Locale::getLanguage,
                        summarizingInt(Locale::hashCode))).get("de").getAverage());
        System.out.println(Stream.of(Locale.getAvailableLocales())
                .collect(groupingBy(Function.<Locale>identity(),
                        reducing("", Locale::getCountry, (s, t) -> s.length() == 0 ? t : s + ", " + t))));
        Stream.iterate(1000, i -> i + 1).limit(10).flatMap(i -> Stream.of(String.valueOf(i).split(""))).forEach(System.out::println);
    }
}
