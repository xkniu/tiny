package org.algorithmprac.sort;

public class SelectionSorter extends AbstractCostAwareSorter {

    @Override
    protected void sortInternal(Comparable[] a) {
        for (int i = 0; i < a.length - 1; ++i) {
            int min = i;
            for (int j = i + 1; j < a.length; ++j) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            swap(a, i, min);
        }
    }

    @Override
    public String getName() {
        return "selection-sort";
    }
}
