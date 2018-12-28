package io.xkniu.github.tiny.core.algorithm.sort;

import java.util.concurrent.TimeUnit;

/**
 * Sorter marker which could get the cost of sorting.
 */
public interface CostAwareSorter extends Sorter {

    String getReadableCost();

    long getCost(TimeUnit timeUnit);
}
