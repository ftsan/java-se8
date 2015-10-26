package java8code.ch2.practice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by futeshi on 2015/10/26.
 */
public class Q13 {
    public static void main(String[] args) throws IOException {
        String contents = new String(
                Files.readAllBytes(Paths.get("build.gradle")), StandardCharsets.UTF_8);
        contents += contents + "\n" + contents + "\n" + contents;
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        Map<Integer, Long> shortWordsCount = words.parallelStream().filter(word -> word.length() < 12)
                .collect(groupingBy(String::length, counting()));
        System.out.println(shortWordsCount);

        Map<Integer, Long> shortWordsCount2 = words.stream().filter(word -> word.length() < 12)
                .collect(groupingBy(String::length, counting()));
        IntStream.range(0, 12).forEach(i -> {
            if (shortWordsCount.get(i) != shortWordsCount2.get(i)) {
                System.out.println(false);
                return;
            }
        });
        System.out.println(true);
    }
}

