package java8code.ch3;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by futeshi on 2015/10/28.
 */
public class Q7Test {
    List<String> words;

    @Before
    public void createWords() {
        words = Arrays.asList("group", "org", "apply", "Group");
    }


    @Test
    public void testAscending() {
        Collections.sort(words, new Q7.StringComparator.Builder().build().getInstance());
        assertThat(words, is(contains("Group", "apply", "group", "org")));
    }
}