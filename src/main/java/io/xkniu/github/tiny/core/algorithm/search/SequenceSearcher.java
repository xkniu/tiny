package io.xkniu.github.tiny.core.algorithm.search;

public class SequenceSearcher implements Searcher {

    @Override
    public int search(int[] a, int value) {
        for (int i = 0; i < a.length; ++i) {
            if (a[i] == value) {
                return i;
            }
        }
        return -1;
    }
}