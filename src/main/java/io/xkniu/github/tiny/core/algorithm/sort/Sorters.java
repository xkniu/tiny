package io.xkniu.github.tiny.core.algorithm.sort;

public class Sorters {

    public static CostAwareSorter getSorter(SorterType sorterType) {
        switch (sorterType) {
            case SELECTION:
                return newSelectionSorter();
            case INSERTION:
                return newInsertionSorter();
            case BUBBLE:
                return newBubbleSorter();
            case SHELL:
                return newShellSorter();
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
    public static CostAwareSorter newShellSorter() {
        return new ShellSorter();
    }

    public enum SorterType {
        SELECTION, INSERTION, BUBBLE, SHELL
    }
}
