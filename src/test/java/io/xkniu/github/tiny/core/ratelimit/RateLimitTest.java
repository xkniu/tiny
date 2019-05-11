package io.xkniu.github.tiny.core.ratelimit;

import io.xkniu.github.tiny.core.base.AbstractTestBase;
import org.junit.Assert;
import org.junit.Test;

public class RateLimitTest extends AbstractTestBase {

    @Test
    public void testTokenBucketRateLimiter() throws Exception {
        RateLimiter rateLimiter = new TokenBucketRateLimiter(10, 100, 1);
        boolean acquiredExceedMax = rateLimiter.tryAcquire(11);
        Assert.assertFalse(acquiredExceedMax);

        while (rateLimiter.tryAcquire(1)) {
            // consume all buckets
        }

        boolean acquireTooFast = rateLimiter.tryAcquire(1);
        Assert.assertFalse(acquireTooFast);

        Thread.sleep(200);
        boolean acquireSuccess = rateLimiter.tryAcquire(1);
        Assert.assertTrue(acquireSuccess);
    }

    @Test
    public void testSlidingWindowRateLimiter() throws Exception {
        RateLimiter rateLimiter = new SlidingWindowRateLimiter(1000, 10);
        while (rateLimiter.tryAcquire(1)) {
            // consume all buckets
        }
        boolean acquireTooFast = rateLimiter.tryAcquire(1);
        Assert.assertFalse(acquireTooFast);
        Thread.sleep(100);
        acquireTooFast = rateLimiter.tryAcquire(1);
        Assert.assertFalse(acquireTooFast);

        Thread.sleep(1200);
        boolean acquireSuccess = rateLimiter.tryAcquire(1);
        Assert.assertTrue(acquireSuccess);
    }

    @Test
    public void testFixedWindowRateLimiter() throws Exception {
        RateLimiter rateLimiter = new FixedWindowRateLimiter(1000, 10);
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(rateLimiter.tryAcquire(1));
        }
        boolean acquireTooFast = rateLimiter.tryAcquire(1);
        Assert.assertFalse(acquireTooFast);
        Thread.sleep(100);
        acquireTooFast = rateLimiter.tryAcquire(1);
        Assert.assertFalse(acquireTooFast);

        Thread.sleep(1100);
        boolean acquireSuccess = rateLimiter.tryAcquire(1);
        Assert.assertTrue(acquireSuccess);
    }
}
