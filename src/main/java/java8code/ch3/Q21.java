package java8code.ch3;

import java.util.concurrent.*;
import java.util.function.Function;

/**
 * Created by futeshi on 2015/11/03.
 */
public class Q21 {
    public static <T, U> Future<U> map(Future<T> future, Function<T, U> f) {
        return new Future<U>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return future.cancel(mayInterruptIfRunning);
            }

            @Override
            public boolean isCancelled() {
                return future.isCancelled();
            }

            @Override
            public boolean isDone() {
                return future.isDone();
            }

            @Override
            public U get() throws InterruptedException, ExecutionException {
                return f.apply(future.get());
            }

            @Override
            public U get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return f.apply(future.get(timeout, unit));
            }
        };
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        Future<String> future = service.submit(() -> "aaa");
        System.out.println(map(future, s -> s.length()).get());

        service.shutdown();

    }
}

