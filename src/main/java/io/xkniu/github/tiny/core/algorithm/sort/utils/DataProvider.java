package io.xkniu.github.tiny.core.algorithm.sort.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class DataProvider {

    private static final ConcurrentHashMap<Integer, Integer[]> dataCache = new ConcurrentHashMap<>();

    public static Integer[] getUnorderedData(int size, int bound) {
        Random random = new Random();
        Integer[] a = new Integer[size];
        for (int i = 0; i < size; ++i) {
            a[i] = random.nextInt(bound);
        }
        return a;
    }

    public static Integer[] getUnorderedUniqueData(int size) {
        Integer[] a = new Integer[size];
        for (int i = 0; i < size; ++i) {
            a[i] = i;
        }
        Collections.shuffle(Arrays.asList(a));
        return a;
    }

    public static Integer[] getUnorderedUniqueDataCopy(int size) {
        if (!dataCache.containsKey(size)) {
            dataCache.putIfAbsent(size, getUnorderedUniqueData(size));
        }
        return dataCache.get(size).clone();
    }
}
