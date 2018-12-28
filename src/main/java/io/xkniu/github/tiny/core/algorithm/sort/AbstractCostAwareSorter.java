package io.xkniu.github.tiny.core.algorithm.sort;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * Base class of sorter to record the count of comparision and swap.
 */
public abstract class AbstractCostAwareSorter extends AbstractSorter implements CostAwareSorter {

    private final Stopwatch stopwatch = Stopwatch.createUnstarted();

    private long cmpCount = 0;

    private long swapCount = 0;

    @Override
    protected void preProcess(Comparable[] a) {
        resetStatistics();
        stopwatch.start();
    }

    private void resetStatistics() {
        stopwatch.reset();
        cmpCount = 0L;
        swapCount = 0L;
    }

    @Override
    protected void postProcess(Comparable[] a) {
        stopwatch.stop();
    }

    @Override
    public boolean less(Comparable v, Comparable w) {
        ++cmpCount;
        return super.less(v, w);
    }

    @Override
    public void swap(Comparable[] a, int i, int j) {
        ++swapCount;
        super.swap(a, i, j);
    }

    public String getReadableCost() {
        StringBuilder sb = new StringBuilder();
        sb.append("cost is ");
        sb.append(stopwatch.toString());
        sb.append(", compare count is ");
        sb.append(cmpCount);
        sb.append(", swap count is ");
        sb.append(swapCount);
        return sb.toString();
    }

    public long getCost(TimeUnit timeUnit) {
        return stopwatch.elapsed(timeUnit);
    }

    public long getCmpCount() {
        return cmpCount;
    }

    public long getSwapCount() {
        return swapCount;
    }
}
