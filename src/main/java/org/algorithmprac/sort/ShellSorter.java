package org.algorithmprac.sort;

public class ShellSorter extends AbstractCostAwareSorter {

    @Override
    protected void sortInternal(Comparable[] a) {
        int h = 1;
        while (h < a.length / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < a.length; ++i /* ++i 不够直观; 更直观的是 i+=h, 但是 i 的起始值要从 [h, 2h), 但效果是等价的 */) {
                for (int j = i; j >= h && less(a[j], a[j - 1]); j -= h) {
                    swap(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    @Override
    public String getName() {
        return "shell-sort";
    }
}
