package java8code.ch1.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ftsan on 2015/10/21.
 */
public class Q8 {

    public static void main(String[] args) {
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (String name : names) {
            runners.add(() -> System.out.println(name));
        }

        runners.stream().forEach(r -> new Thread(r).start());

        List<Runnable> runners2 = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            // 事実上のfinalの値であればコンパイルは通る
            final int finalI = i;
            runners2.add(() -> System.out.println(names[finalI]));
        }
        runners2.stream().forEach(r -> new Thread(r).start());
    }
}
