package io.xkniu.github.tiny.core.retry;

import java.util.function.Supplier;

public class RetryTemplate {

    public static <T> T execute(Supplier<T> Supplier, RetryPolicy retryPolicy, RetryCondition retryCondition) {
        int attempts = 0;
        while (true) {
            try {
                return Supplier.get();
            } catch (Exception e) {
                attempts++;
                if (attempts >= retryPolicy.getMaxAttempts() || !retryCondition.shouldRetry(e)) {
                    throw new RuntimeException("Max retry attempts reached", e);
                }
                long delay = retryPolicy.getDelayInMills(attempts);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }
            }
        }
    }
}
