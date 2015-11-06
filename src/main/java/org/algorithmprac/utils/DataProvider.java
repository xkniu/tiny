package org.algorithmprac.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class DataProvider {

    public static Integer[] getUnorderedIntegerArray(int size, int bound) {
        Random random = new Random();
        Integer[] a = new Integer[size];
        for (int i = 0; i < size; ++i) {
            a[i] = random.nextInt(bound);
        }
        return a;
    }

    public static Integer[] getUnorderedUniqueIntegerArray(int size) {
        Integer[] a = new Integer[size];
        for (int i = 0; i < size; ++i) {
            a[i] = i;
        }
        Collections.shuffle(Arrays.asList(a));
        return a;
    }
}
