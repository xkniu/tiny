package org.algorithmprac.sort;

public class Sorters {

    public static CostAwareSorter getSorter(SorterType sorterType) {
        switch (sorterType) {
            case SELECTION:
                return newSelectionSorter();
            case INSERTION:
                return newInsertionSorter();
            case BUBBLE:
                return newBubbleSorter();
            default:
                return newDefaultSorter();
        }
    }

    public static CostAwareSorter newDefaultSorter() {
        return newSelectionSorter();
    }

    public static CostAwareSorter newSelectionSorter() {
        return new SelectionSorter();
    }

    public static CostAwareSorter newBubbleSorter() {
        return new BubbleSorter();
    }

    public static CostAwareSorter newInsertionSorter() {
        return new InsertionSorter();
    }

    public enum SorterType {
        SELECTION, INSERTION, BUBBLE
    }
}