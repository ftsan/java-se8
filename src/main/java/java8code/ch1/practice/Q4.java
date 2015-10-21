package java8code.ch1.practice;

import java.io.File;
import java.util.Arrays;

/**
 * Created by futeshi on 2015/10/21.
 */
public class Q4 {
    public static void main(String[] args) {
        File f = new File(System.getProperty("user.dir"));
        System.out.println(Arrays.asList(sortPreferDirAndFileName(f.listFiles())));
    }

    private static File[] sortPreferDirAndFileName(File[] files) {
        Arrays.sort(files, (File a, File b) -> {
            if((a.isDirectory() && b.isDirectory()) || a.isFile() && b.isFile()) {
                return a.compareTo(b);
            }
            return a.isDirectory() ? -1 : 1;
        });
        return files;
    }
}
