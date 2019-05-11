package io.xkniu.github.tiny.core.algorithm.search;

public class BinarySearcher implements Searcher {

    @Override
    public int search(int[] a, int value) {
        return search0(a, value);
    }

    /**
     * C++ STL lower_bound 的写法
     */
    private int search0(int[] a, int value) {
        int l = 0;
        int r = a.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (a[mid] < value) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return r == a.length ? -1 : r;
    }
}
