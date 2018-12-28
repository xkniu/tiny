package io.xkniu.github.tiny.core.algorithm.sort;

/**
 * Sorter.
 */
public interface Sorter {

    String getName();

    void show(Comparable[] a);

    void sort(Comparable[] a);
}
