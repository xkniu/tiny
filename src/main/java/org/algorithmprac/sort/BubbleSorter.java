package org.algorithmprac.sort;

public class BubbleSorter extends AbstractCostAwareSorter {

    @Override
    protected void sortInternal(Comparable[] a) {
        for (int i = a.length - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (less(a[j + 1], a[j])) {
                    swap(a, j, j + 1);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "bubble-sort";
    }
}
