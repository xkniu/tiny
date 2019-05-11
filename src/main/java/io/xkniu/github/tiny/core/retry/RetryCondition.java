package io.xkniu.github.tiny.core.retry;

public interface RetryCondition {

    boolean shouldRetry(Exception e);
}
