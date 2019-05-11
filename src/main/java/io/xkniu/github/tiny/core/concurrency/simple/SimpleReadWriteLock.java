package io.xkniu.github.tiny.core.concurrency.simple;

/**
 * 使用两个 int 实现一个十分简易的读写锁，不考虑可重入问题，不考虑公平性问题，不考虑锁升级问题。
 *
 * 假设实际使用场景，读请求远多于写请求，需要避免写请求饥饿。
 */
public class SimpleReadWriteLock {

    /** 读锁 */
    private static final int READ_LOCK = 0x1;

    /** 写锁 */
    private static final int WRITE_LOCK = 0x1;

    /** 写意向锁 */
    private static final int WRITE_INTEND_LOCK = 0x1FFFF;

    /** 写锁掩码 */
    private static final int WRITE_LOCK_MASK = 0xFFFF;

    /** 最大读锁数量 */
    private static final int READ_MAX_COUNT = 0x7FFFFFFF;

    /** 最大写锁数量 */
    private static final int WRITE_MAX_COUNT = 0x7FFF;

    /** 私有锁，不允许外部干涉同步机制 */
    private final Object lock = new Object();

    /** 读请求数 */
    private int read = 0;

    /** 低16位：写请求数；高16位：有写意向请求数。
     *
     * 由于写锁互斥，其实只用一个比特位就够了，可以多分配点写意向的位数。
     * 这里随意选一个实现了，主要体现读写锁思路，另外如果改造支持重入的话也需要更多的比特位来给写锁。
     */
    private int write = 0;

    public void lockRead() throws InterruptedException {
        synchronized (lock) {
            while (isWritingOrIntentWriting()) {
                wait();
            }

            acquireRead();
        }
    }

    public synchronized void lockWrite() throws InterruptedException {
        synchronized (lock) {
            // 先加上加锁意向，插队读请求，防止写锁饥饿
            // 加上锁意向后才可以 wait
            acquireWriteIntent();

            while (isWriting()) {
                wait();
            }

            while (isReading()) {
                wait();
            }

            acquireWrite();
            // 释放意向锁
            write -= WRITE_INTEND_LOCK;
        }
    }

    public synchronized void unlockRead() {
        synchronized (lock) {
            read -= READ_LOCK;

            // 可能读写线程都在等待，等待的线程不同质，应唤醒所有，防止只唤醒了无法处理的线程
            notifyAll();
        }
    }

    public synchronized void unlockWrite() {
        synchronized (lock) {
            write -= WRITE_LOCK;
            notifyAll();
        }
    }

    private boolean isReading() {
        return read > 0;
    }

    private boolean isWritingOrIntentWriting() {
        return write > 0;
    }

    private boolean isWriting() {
        return (write & WRITE_LOCK_MASK) > 0;
    }

    private void acquireRead() {
        if (read == READ_MAX_COUNT) {
            throw new IllegalArgumentException("Too many reads");
        }
        read += READ_LOCK;
    }

    private void acquireWrite() {
        if (writeCount() == WRITE_MAX_COUNT) {
            throw new IllegalArgumentException("Too many writes");
        }
        write += WRITE_LOCK;
    }

    private void acquireWriteIntent() {
        if (writeIntentCount() == WRITE_MAX_COUNT) {
            throw new IllegalArgumentException("Too many writes");
        }
        write += WRITE_INTEND_LOCK;
    }

    private int writeCount() {
        return write & WRITE_LOCK_MASK;
    }

    private int writeIntentCount() {
        return write >>> 16;
    }
}