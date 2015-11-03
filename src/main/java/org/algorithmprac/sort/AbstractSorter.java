package org.algorithmprac.sort;

public abstract class AbstractSorter implements Sorter {

    public void sort(Comparable[] a) {
        preProcess(a);
        sortInternal(a);
        postProcess(a);
        assert isSorted(a) : "Sorting is incorrect!!!";
    }

    protected abstract void preProcess(Comparable[] a);

    protected abstract void sortInternal(Comparable[] a);

    protected abstract void postProcess(Comparable[] a);

    public boolean less(Comparable v, Comparable w) {
        return lessInternal(v, w);
    }

    private boolean lessInternal(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public void swap(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public void show(Comparable[] a) {
        for (Comparable u : a) {
            System.out.print(u + " ");
        }
        System.out.println();
    }

    public final boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; ++i) {
            if (lessInternal(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }
}
