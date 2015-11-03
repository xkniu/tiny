package org.algorithmprac.sort;

public class InsertionSorter extends AbstractCostAwareSorter {

    @Override
    protected void sortInternal(Comparable[] a) {
        for (int i = 1; i < a.length; ++i) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); --j) {
                swap(a, j, j - 1);
            }
        }
    }

    @Override
    public String getName() {
        return "insertion-sort";
    }
}
