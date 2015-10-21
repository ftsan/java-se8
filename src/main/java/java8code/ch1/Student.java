package java8code.ch1;

/**
 * Created by futeshi on 2015/10/21.
 */
public class Student extends Person implements Named {
    @Override
    public long getId() {
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(new Student().getName());
    }
}
