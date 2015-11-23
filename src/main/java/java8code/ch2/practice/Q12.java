package java8code.ch2.practice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ftsan on 2015/10/26.
 */
public class Q12 {
    public static void main(String[] args) throws IOException {
        String contents = new String(
                Files.readAllBytes(Paths.get("build.gradle")), StandardCharsets.UTF_8);
        contents += contents + "\n" + contents + "\n" + contents;
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        AtomicInteger[] shortWords = new AtomicInteger[12];
        IntStream.range(0, 12).forEach(i -> shortWords[i] = new AtomicInteger(0));
        words.parallelStream().forEach(s -> {
            if (s.length() < 12) {
                shortWords[s.length()].getAndIncrement();
            }
        });
        Stream.of(shortWords).forEach(System.out::println);

        AtomicInteger[] shortWords2 = new AtomicInteger[12];
        IntStream.range(0, 12).forEach(i -> shortWords2[i] = new AtomicInteger(0));
        words.stream().forEach(s -> {
            if (s.length() < 12) {
                shortWords2[s.length()].getAndIncrement();
            }
        });
        IntStream.range(0, 12).forEach(i -> {
            if (shortWords[i].get() != shortWords2[i].get()) {
                System.out.println(false);
                return;
            }
        });
        System.out.println(true);
    }
}
