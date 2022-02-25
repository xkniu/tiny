package io.xkniu.github.tiny.core.concurrency.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个简易的 fixed 线程池实现，初始线程数量为 0。
 *
 * 支持立刻关闭和重新设置线程池大小。
 */
public class SimpleThreadPool {

    private final Lock mainLock = new ReentrantLock();

    private final List<Worker> workers = new ArrayList<>();

    private final BlockingQueue<Runnable> tasks = new LinkedBlockingDeque<>();

    private volatile boolean stop = false;

    private int poolSize;

    public SimpleThreadPool(int poolSize) {
        this.poolSize = poolSize;
    }

    public void submit(Runnable task) {
        if (stop) {
            return;
        }
        mainLock.lock();
        try {
            if (getWorkerCount() < poolSize) {
                addWorker(task);
            } else {
                tasks.offer(task);
            }
        } finally {
            mainLock.unlock();
        }
    }

    public void setPoolSize(int n) {
        mainLock.lock();
        try {
            int wc = getWorkerCount();
            if (wc > n) {
                while (wc-- > 0) {
                    Worker worker = workers.remove(0);
                    worker.interruptIfIdle();
                }
            } else {
                poolSize = n;
            }
        } finally {
            mainLock.unlock();
        }
    }

    private void addWorker(Runnable task) {
        Worker worker = new Worker(task);
        new Thread(worker).start();
        workers.add(worker);
    }

    public void shutdownNow() {
        stop = true;
        mainLock.lock();
        try {
            for (Worker worker : workers) {
                worker.interrupt();
            }
        } finally {
            mainLock.unlock();
        }

    }

    private int getWorkerCount() {
        return workers.size();
    }

    private final class Worker implements Runnable {

        private Runnable firstTask;

        private volatile boolean stop;

        private volatile boolean idle = false;

        private Worker(Runnable firstTask) {
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    if (firstTask != null) {
                        firstTask.run();
                        firstTask = null;
                    } else {
                        mainLock.lock();
                        try {
                            Runnable task;
                            try {
                                task = tasks.take();
                                idle = false;
                            } catch (InterruptedException e) {
                                break;
                            }
                            task.run();
                            idle = true;
                        } finally {
                            mainLock.unlock();
                        }
                    }
                } catch (RuntimeException ignore) {
                }
            }
        }

        public void interruptIfIdle() {
            mainLock.lock();
            try {
                this.stop = true;
                if (idle) {
                    // 从 take 中唤醒
                    Thread.currentThread().interrupt();
                }
            } finally {
                mainLock.unlock();
            }
        }

        public void interrupt() {
            this.stop = true;
            Thread.currentThread().interrupt();
        }
    }
}