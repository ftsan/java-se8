package java8code.ch3;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import java.util.stream.Stream;

/**
 * Created by futeshi on 2015/10/27.
 */
public class Q1 {
    private Logger logger;

    public static void main(String[] args) {
        Q1 q1 = new Q1();
        Integer[] a = Stream.iterate(0, i -> i + 1).limit(20).toArray(count -> new Integer[count]);
        int i = 10;

        q1.logger = Logger.getLogger("Q1");

        // コンソールへの出力はレベルがINFO以上しか出力されない
        q1.logIf(Level.FINE, () -> {
            System.out.println("判定");
            return i == 10;
        }, () -> "a[1] = " + a[1]);

        q1.logIf(Level.INFO, () -> {
            System.out.println("判定");
            return i == 1;
        }, () -> "a[2] = " + a[2]);
        q1.logIf(Level.INFO, () -> {
            System.out.println("判定");
            return i == 10;
        }, () -> "a[3] = " + a[3]);

        q1.logIf(Level.WARNING, () -> {
            System.out.println("判定");
            return i == 10;
        }, () -> "a[4] = " + a[4]);
    }

    public void logIf(Level level, BooleanSupplier condition, Supplier<String> message) {
        if (logger.isLoggable(level)) {
            if (condition.getAsBoolean()) {
                logger.log(level, message.get());
            }
        }
    }
}
