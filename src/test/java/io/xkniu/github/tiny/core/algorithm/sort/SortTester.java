package io.xkniu.github.tiny.core.algorithm.sort;

import io.xkniu.github.tiny.core.algorithm.sort.utils.DataProvider;
import io.xkniu.github.tiny.core.base.AbstractTestBase;
import org.junit.Test;

public class SortTester extends AbstractTestBase {

    @Test
    public void testSelectionSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.SELECTION);
        costAwareSorter.sort(DataProvider.getUnorderedUniqueDataCopy(10000));
        logger.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }

    @Test
    public void testInsertionSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.INSERTION);
        costAwareSorter.sort(DataProvider.getUnorderedUniqueDataCopy(10000));
        logger.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }

    @Test
    public void testBubbleSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.BUBBLE);
        costAwareSorter.sort(DataProvider.getUnorderedUniqueDataCopy(10000));
        logger.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }

    @Test
    public void testShellSort() {
        CostAwareSorter costAwareSorter = Sorters.getSorter(Sorters.SorterType.SHELL);
        costAwareSorter.sort(DataProvider.getUnorderedUniqueDataCopy(10000));
        logger.info("{}, {}", costAwareSorter.getName(), costAwareSorter.getReadableCost());
    }
}
