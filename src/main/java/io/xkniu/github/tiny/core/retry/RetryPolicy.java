package io.xkniu.github.tiny.core.retry;

public interface RetryPolicy {

    int getMaxAttempts();

    long getDelayInMills(int attempt);
}
