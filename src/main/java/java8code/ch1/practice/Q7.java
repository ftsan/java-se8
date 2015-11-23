package java8code.ch1.practice;

/**
 * Created by ftsan on 2015/10/21.
 */
public class Q7 {
    public static void main(String[] args) {
        new Thread(andThen(() -> System.out.println("r1"), () -> System.out.println("r2"))).start();
    }

    public static Runnable andThen(Runnable r1, Runnable r2) {
        new Thread(r1).start();
        return () -> r2.run();
    }
}
