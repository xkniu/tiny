package io.xkniu.github.tiny.core.retry;

import io.xkniu.github.tiny.core.base.AbstractTestBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Supplier;

public class RetryTest extends AbstractTestBase {

    @Test
    public void testNonRetry() {
        RetryPolicy retryPolicy = new FixedDelayPolicy(100, 2);
        RetryCondition retryCondition = new AllExceptionRetryCondition();

        int result = RetryTemplate.execute(() -> 1, retryPolicy, retryCondition);
        Assert.assertEquals("retry success", 1, result);
    }

    @Test
    public void testRetrySuccess() {
        RetryPolicy retryPolicy = new FixedDelayPolicy(100, 2);
        RetryCondition retryCondition = new AllExceptionRetryCondition();

        int result = RetryTemplate.execute(new Supplier<>() {
            int count = 0;

            @Override
            public Integer get() {
                if (count++ == 0) {
                    throw new RuntimeException("Execute error");
                }
                return 1;
            }
        }, retryPolicy, retryCondition);
        Assert.assertEquals("retry success", 1, result);
    }

    @Test(expected = RuntimeException.class)
    public void testRetryFailed() {
        RetryPolicy retryPolicy = new FixedDelayPolicy(100, 2);
        RetryCondition retryCondition = new AllExceptionRetryCondition();

        int result = RetryTemplate.execute(new Supplier<>() {
            int count = 0;

            @Override
            public Integer get() {
                if (count++ <= 2) {
                    throw new RuntimeException("Execute error");
                }
                return 1;
            }
        }, retryPolicy, retryCondition);
        Assert.assertEquals("retry success", 1, result);
    }
}
