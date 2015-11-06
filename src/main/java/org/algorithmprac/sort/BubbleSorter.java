package org.algorithmprac.sort;

/**
 * 冒泡排序 [stable] [!online].
 * 冒泡排序的时间复杂度为 Ω(n^2), 比较次数始终为 Ω(n^2), 交换次数为数组中 [逆序数个数].
 */
public class BubbleSorter extends AbstractCostAwareSorter {

    @Override
    protected void sortInternal(Comparable[] a) {
        // 元素的最终冒泡位置
        // 可以优化为如果某次没有任何交换, 可以立刻结束排序
        for (int i = a.length - 1; i > 0; --i) {
            // 从下往上冒泡
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
