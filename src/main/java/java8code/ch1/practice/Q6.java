package java8code.ch1.practice;

/**
 * Created by ftsan on 2015/10/21.
 */
public class Q6 {
    public static void main(String[] args) {
        new Thread(uncheck(() -> {
            System.out.println("Zzz");
            Thread.sleep(1000);
            throw new Exception("error");
        })).start();
    }

    private static Runnable uncheck(RunnableEx ur) {
        return () -> {
            try {
                ur.run();
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        };
    }

    @FunctionalInterface
    interface RunnableEx {
        void run() throws Throwable;
    }
}
