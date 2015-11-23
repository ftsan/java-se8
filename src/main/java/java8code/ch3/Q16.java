package java8code.ch3;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by ftsan on 2015/10/31.
 */
public class Q16 {
    public static void main(String[] args) {
        doInOrderAsync(
                ()     -> {
                    throw new RuntimeException("error");
                },
                (s, t) -> {
                    if (t != null) {
                        System.err.println(t.getMessage());
                    } else {
                        System.out.println(s);
                    }
                });
    }

    public static <T> void doInOrderAsync(Supplier<T> first, BiConsumer<T, Throwable> second) {
        Thread thread = new Thread(() -> {
            T result = null;
            try {
                result = first.get();
                second.accept(result, null);
            } catch (Throwable t) {
                second.accept(result, t);
            }
        });
        thread.start();
    }


    public static <T> void doInOrderAsync(
            Supplier<T> first, Consumer<T> second, Consumer<Throwable> handler) {
         Thread t = new Thread(() -> {
             try {
                 T result = first.get();
                 second.accept(result);
             } catch (Throwable throwable) {
                 handler.accept(throwable);
             }
         });
        t.start();
    }
}
