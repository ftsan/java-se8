package java8code.ch3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by futeshi on 2015/10/27.
 */
public class Q2 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(false);
        withLock(lock, () -> System.out.println("Lock"));
    }

    private static void withLock(ReentrantLock lock, Runnable r) {
        lock.lock();
        try {
            r.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
