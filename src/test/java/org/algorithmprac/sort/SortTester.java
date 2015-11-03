package org.algorithmprac.sort;

import org.algorithmprac.utils.DataProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortTester {

    private static final Logger log = LoggerFactory.getLogger(SortTester.class);

    @Test
    public void testSelectionSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.SELECTION);
        costAwareSorter.sort(prepareData());
        log.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }

    @Test
    public void testInsertionSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.INSERTION);
        costAwareSorter.sort(prepareData());
        log.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }

    @Test
    public void testBubbleSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.BUBBLE);
        costAwareSorter.sort(prepareData());
        log.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }

    private Integer[] prepareData() {
        return DataProvider.getUnorderedIntegerArray(500, 10000);
    }
}
