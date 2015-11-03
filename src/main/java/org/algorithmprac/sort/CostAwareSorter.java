package org.algorithmprac.sort;

import java.util.concurrent.TimeUnit;

public interface CostAwareSorter extends Sorter {

    String getReadableCost();

    long getCost(TimeUnit timeUnit);
}
