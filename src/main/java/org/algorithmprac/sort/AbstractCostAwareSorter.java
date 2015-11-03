package org.algorithmprac.sort;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

public abstract class AbstractCostAwareSorter extends AbstractSorter implements CostAwareSorter {

    private final Stopwatch stopwatch = Stopwatch.createUnstarted();

    private int cmpCount = 0;

    private int swapCount = 0;

    @Override
    protected void preProcess(Comparable[] a) {
        resetStatistics();
        stopwatch.start();
    }

    private void resetStatistics() {
        stopwatch.reset();
        cmpCount = 0;
        swapCount = 0;
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

    public int getCmpCount() {
        return cmpCount;
    }

    public int getSwapCount() {
        return swapCount;
    }
}
