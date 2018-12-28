package io.xkniu.github.tiny.core.algorithm.sort;

/**
 * 插入排序 [stable] [online].
 *
 * 插入排序的时间复杂度为 O(n^2), 交换次数为数组中 [逆序数个数], 比较次数最少为 [逆序数个数], 最多为 [逆序数个数 + n - 1].
 */
public class InsertionSorter extends AbstractCostAwareSorter {

    @Override
    protected void sortInternal(Comparable[] a) {
        // 待插入的元素
        for (int i = 1; i < a.length; ++i) {
            // 待插入元素向有序数组中插入
            for (int j = i; j > 0 && less(a[j], a[j - 1]); --j) {
                // 可将交换优化为寻找到合适位置后, 将内存区域整体右移
                swap(a, j, j - 1);
            }
        }
    }

    @Override
    public String getName() {
        return "insertion-sort";
    }
}
