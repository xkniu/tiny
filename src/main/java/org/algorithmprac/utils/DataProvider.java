package org.algorithmprac.utils;

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
}
