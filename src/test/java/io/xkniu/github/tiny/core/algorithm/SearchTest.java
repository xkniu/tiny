package io.xkniu.github.tiny.core.algorithm;

import io.xkniu.github.tiny.core.algorithm.search.SequenceSearcher;
import io.xkniu.github.tiny.core.base.AbstractTestBase;
import org.junit.Assert;
import org.junit.Test;

public class SearchTest extends AbstractTestBase {

    @Test
    public void testSequenceSearch() {
        SequenceSearcher searcher = new SequenceSearcher();
        int i = searcher.search(new int[] {1, 2, 3, 4, 5, 6, 7}, 5);
        Assert.assertEquals(4, i);
    }
}