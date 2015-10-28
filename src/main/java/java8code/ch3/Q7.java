package java8code.ch3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by futeshi on 2015/10/27.
 */
public class Q7 {
    public static void main(String[] args) throws IOException {
        String contents = new String(
                Files.readAllBytes(Paths.get("build.gradle")), StandardCharsets.UTF_8);
        contents += contents + "\n" + contents + "\n" + contents;
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        Collections.sort(words, new StringComparator.Builder().build().getInstance());
        words.stream().forEach(System.out::println);
    }

    public static class StringComparator {
        private Sort sort = Sort.ASCENDING;
        private Sensitive sensitive = Sensitive.SENSITIVITY;
        private Blank blank = Blank.CONSIDER;

        private StringComparator(Builder builder) {
            this.sort = builder.sort;
            this.sensitive = builder.sensitive;
            this.blank = builder.blank;
        }

        static class Builder {
            private Sort sort = Sort.ASCENDING;
            private Sensitive sensitive = Sensitive.SENSITIVITY;
            private Blank blank = Blank.CONSIDER;

            public Builder sort(Sort sort) {
                this.sort = sort;
                return this;
            }

            public Builder sensitive(Sensitive sensitive) {
                this.sensitive = sensitive;
                return this;
            }

            public Builder blank(Blank blank) {
                this.blank = blank;
                return this;
            }

            public StringComparator build() {
                return new StringComparator(this);
            }
        }

        public Comparator<String> getInstance() {
            return (o1, o2) -> sensitive.sensitive().apply(blank.blank().apply(o1), blank.blank().apply(o2)) * sort.sort();
        }

        /**
         * ソート順
         */
        public enum Sort {
            ASCENDING, REVERSE;

            int sort() {
                if (this == ASCENDING) {
                    return 1;
                }
                return -1;
            }
        }

        /**
         * 大文字・小文字を考慮するかしないか
         */
        public enum Sensitive {
            SENSITIVITY, INSENSITIVITY;

            BiFunction<String, String, Integer> sensitive() {
                if (this == SENSITIVITY) {
                    return (a, b) -> a.compareTo(b);
                }
                return (a, b) -> a.compareToIgnoreCase(b);
            }
        }

        /**
         * 空白を考慮するかしないか
         */
        public enum Blank {
            CONSIDER, EXCLUDE;

            Function<String, String> blank() {
                if (this == CONSIDER) {
                    return s -> s;
                }
                return s -> s.replaceAll(" ", "");
            }
        }

    }
}
