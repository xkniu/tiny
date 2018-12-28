package io.xkniu.github.tiny.core.algorithm.sort;

/**
 * 选择排序 [!stable] [!online].
 *
 * 时间复杂度为 Ω(n^2), 比较次数始终为 Ω(n^2), 交换次数为 [n-1].
 */
public class SelectionSorter extends AbstractCostAwareSorter {

    @Override
    protected void sortInternal(Comparable[] a) {
        // 待填充的数组位置
        for (int i = 0; i < a.length - 1; ++i) {
            // 寻找剩余所有元素中最小元素
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
