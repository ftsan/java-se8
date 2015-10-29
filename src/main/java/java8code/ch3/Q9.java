package java8code.ch3;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by futeshi on 2015/10/29.
 */
public class Q9 {
    public static void main(String[] args) {
        Person p1 = new Person("Java", "Java");
        Person p2 = new Person("Java", "Java2");
        Person p3 = new Person("Java", "Java3");

        List<Person> persons = Arrays.asList(p2, p3, p1);
        Collections.sort(persons, lexicographicCoparator("lastName", "firstName"));
        persons.stream().forEach(p -> System.out.println(p.firstName + " " + p.lastName));
    }

    public static <T> Comparator<T> lexicographicCoparator(String... fieldNames) {
        return (o1, o2) -> {
            try {
                for (String s : fieldNames) {
                    Object of1 = o1.getClass().getDeclaredField(s).get(o1);
                    Object of2 = o2.getClass().getDeclaredField(s).get(o2);
                    if (of1.equals(of2)) {
                        System.out.println(of1.toString() + ":" + of2.toString());
                        continue;
                    }
                    return ((Comparable) of1).compareTo(of2);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return 0;
        };
    }

    static class Person {
        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String lastName;
        public String firstName;
    }
}
