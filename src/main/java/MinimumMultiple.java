import java.util.stream.LongStream;


public class MinimumMultiple {
    public static long minimumMultiple(long n) {
        for(int i = 1; i < Integer.MAX_VALUE; i++) {
            long divided = i * n;
            for(long j = n; j > 0; j--) {
                if (divided % j != 0) {
                    break;
                }

                if(j == 2) {
                    return divided;
                }
            }
        }
        return 0;
    }

    public static long minimumMultiple2(long n) {
        return LongStream.rangeClosed(2, n).reduce(2, (a, b) -> lcm(a, b));
    }

    private static long lcm(long x, long y) {
        return x * y / gcd(x, y);
    }

    private static long gcd(long x, long y) {
        return x == 0l ? y : gcd(y % x, x);
    }


    public static void main(String[] args) {
        for (int i = 3; i < 21; i++) {
            System.out.println("i=" + i + ", answer=" + minimumMultiple(i));
        }
            System.out.println("answer=" + minimumMultiple2((long)20));
    }
}
