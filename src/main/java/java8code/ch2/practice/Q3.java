package java8code.ch2.practice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by futeshi on 2015/10/24.
 */
public class Q3 {
    public static void main(String[] args) throws IOException {
        String contents = IntStream.range(0, 2500000).boxed()
                .map(i -> {
                    try {
                        return String.valueOf(Files.readAllBytes(Paths.get("build.gradle")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .collect(Collectors.joining("\n"));

        Stream<String> stream = Arrays.asList(contents.split("[\\P{L}]+")).stream();
        long start1 = System.nanoTime();
        stream.filter(s -> s.length() > 5).count();
        System.out.println((System.nanoTime() - start1) / 1000);

        Stream<String> parallell = Arrays.asList(contents.split("[\\P{L}]+")).stream().parallel();
        long start2 = System.nanoTime();
        parallell.filter(s -> s.length() > 5).count();
        System.out.println((System.nanoTime() - start2) / 1000);
    }
}
