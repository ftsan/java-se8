package java8code.ch1.practice;

import java.util.Arrays;

/**
 * Created by ftsan on 2015/10/21.
 */
public class Q1 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        String[] arr = {"1","2","3"};
        Arrays.sort(arr,(a, b) -> {
            System.out.println(Thread.currentThread().getName());
            return Integer.compare(a.length(), b.length());
        });
    }
}
