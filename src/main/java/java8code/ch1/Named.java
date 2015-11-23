package java8code.ch1;

/**
 * Created by ftsan on 2015/10/21.
 */
public interface Named {
//    default String getName() {
//        return getClass().getName() + "_" + hashCode();
//    }
    default String getName() {
        return "unknouwn";
    }
}
