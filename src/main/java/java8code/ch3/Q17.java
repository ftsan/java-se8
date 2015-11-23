package java8code.ch3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by ftsan on 2015/11/01.
 */
public class Q17 {
    public static void doInParallelAsync(Runnable first, Runnable second, Consumer<Throwable> handler) {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (Runnable r : new Runnable[]{first, second}) {
            pool.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getId());
                    r.run();
                } catch (Throwable t) {
                    handler.accept(t);
                }
            });
        }

        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            handler.accept(e);
        }
    }

    public static void main(String[] args) {
        doInParallelAsync(
                () -> System.out.println("first"),
                () -> {
                    System.out.println("second");
                    throw new RuntimeException("error");
                },
                t -> System.err.println(t.getMessage()));
    }
}
