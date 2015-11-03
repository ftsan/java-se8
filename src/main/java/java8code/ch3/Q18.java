package java8code.ch3;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by futeshi on 2015/11/01.
 */
public class Q18 {
    public static <T, U> Function<T, U> unchecked(UncheckedFunction<T, U> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (Throwable throwable) {
                throw throwable;
            }
        };
    }

    public static void main(String[] args) {
        Function<String, List> f = unchecked(t -> fileToList(t));
        System.out.println(f.apply("build.gradle"));
    }

    public static List<String> fileToList(String fileName) throws IOException {
        return Files.lines(FileSystems.getDefault().getPath("", fileName)).collect(Collectors.toList());
    }

    public interface UncheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }
}
