package org.algorithmprac.sort;

import org.algorithmprac.utils.DataProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortTester {

    private static final Logger log = LoggerFactory.getLogger(SortTester.class);

    private static Integer[] testCaseData;

    static {
        testCaseData = DataProvider.getUnorderedIntegerArray(100, 10000);
    }

    private static Integer[] getTestCaseDataCopy() {
        return testCaseData.clone();
    }

    @Test
    public void testSelectionSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.SELECTION);
        costAwareSorter.sort(getTestCaseDataCopy());
        log.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }

    @Test
    public void testInsertionSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.INSERTION);
        costAwareSorter.sort(getTestCaseDataCopy());
        log.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }

    @Test
    public void testBubbleSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.BUBBLE);
        costAwareSorter.sort(getTestCaseDataCopy());
        log.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }
}
