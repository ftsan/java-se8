package java8code.ch1.practice;

/**
 * Created by futeshi on 2015/10/22.
 */
public class Q11 {
    interface i {
        void f();
    }

    interface j {
        static void f() {
            System.out.println("call j.f");
        }
    }

    static class S {
        public void f() {
            System.out.println("call S.f");
        }
    }

    static class K implements i, j {
        @Override
        public void f() {

        }


    }

    public static void main(String[] args) {
        new K().f();
        j.f();
    }
}
