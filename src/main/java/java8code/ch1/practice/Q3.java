package java8code.ch1.practice;

import java.io.File;
import java.util.Arrays;

/**
 * Created by futeshi on 2015/10/21.
 */
public class Q3 {
    public static void main(String[] args) {
        File f = new File(System.getProperty("user.dir"));
        System.out.println(Arrays.asList(findByExtention(f, ".gradle")));
        System.out.println(Arrays.asList(f.list()));
    }

    private static String[] findByExtention(File dir, String extention) {
        return dir.list((f, s) -> s.endsWith(extention));
    }
}
