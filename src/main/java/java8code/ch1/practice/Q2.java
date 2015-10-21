package java8code.ch1.practice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by futeshi on 2015/10/21.
 */
public class Q2 {
    public static void main(String[] args) {
        File f = new File(System.getProperty("user.dir"));
        System.out.println(recursiveDirLists(f));
    }

    private static List<File> recursiveDirLists(File file) {
        List<File> dirNames = new ArrayList<>();
        Stream.of(file.listFiles()).forEach(f -> {
            if (f.isDirectory()) {
                dirNames.add(f);
                dirNames.addAll(recursiveDirLists(f));
            }
        });
        return dirNames;
    }
}
