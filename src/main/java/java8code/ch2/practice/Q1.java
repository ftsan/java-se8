package java8code.ch2.practice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by futeshi on 2015/10/23.
 */
public class Q1 {
    static int count = 0;

    public static void main(String[] args) throws IOException {
        String contents = new String(
                Files.readAllBytes(Paths.get("build.gradle")), StandardCharsets.UTF_8);
        contents += contents + "\n" + contents + "\n" + contents;
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        int counter = 0;
        while (counter < words.size()) {
            List<Thread> threads = IntStream.range(0, 5).boxed().map(i -> {
                return new Thread(() -> {
                    if (words.get(i).length() >= 5) count();
                });
            }).collect(Collectors.toList());
            threads.stream().forEach(t -> t.start());
            counter += 5;
        }

        System.out.println(count);
        System.out.println(words.parallelStream().filter(word -> word.length() >= 5).count());
    }

    synchronized static void count() {
        count++;
    }
}
