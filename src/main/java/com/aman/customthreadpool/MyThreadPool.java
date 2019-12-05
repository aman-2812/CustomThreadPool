package com.aman.customthreadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Implementation of Thread pool
 */
public class MyThreadPool {

    private static final Logger logger= LoggerFactory.getLogger(MyThreadPool.class);

    private final LinkedBlockingQueue<Runnable> queue;
    private int shutdown = 0;
    private Thread[] threads;

    public MyThreadPool(int size) {
        queue = new LinkedBlockingQueue<>();
        TaskProcessor task = new TaskProcessor();
        threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }
    }

    /**
     * Executes the given command at some time in the future.
     *
     * @params a runnable task
     * @throws RejectedExecutionException if this task cannot be
     * accepted for execution
     * @throws NullPointerException if command is null
     */
    public void execute(Runnable task) {
        logger.info("Executing task {}",task);
        if (shutdown != 0) {
            throw new RejectedExecutionException();
        }
        if (task == null) {
            throw new NullPointerException();
        }
        queue.offer(task);
    }

    /**
     * Initiates an orderly shutdown in which previously submitted
     * tasks are executed, but no new tasks will be accepted.
     * Invocation has no additional effect if already shut down.
     */
    public void shutdown() {
        this.shutdown = 1;
        logger.debug("Shutdown initiated");
    }

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task.
     *
     * @params task the task to submit
     * @return a Future representing pending completion of the task
     * @throws NullPointerException if the task is null
     */
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) {
            throw new NullPointerException();
        }
        RunnableFuture<T> futureTask = new FutureTask<>(task);
        execute(futureTask);
        return futureTask;
    }

    /**
     * Returns {@code true} if all tasks have completed following shut down.
     * Note that {@code isTerminated} is never {@code true} unless
     * either {@code shutdown} or {@code shutdownNow} was called first.
     *
     * @return {@code true} if all tasks have completed following shut down
     */
    public boolean isTerminated() {
        if (shutdown != 1)
            return false;
        boolean state = false;
        for (Thread thread : threads) {
            if (thread.getState() != Thread.State.TERMINATED) {
                state = true;
            }
        }
        return !state;
    }

    class TaskProcessor implements Runnable {

        @Override
        public void run() {
            Runnable task = null;
            while (shutdown != 1 || !queue.isEmpty()) {
                try {
                    task = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.debug("Task started by " + Thread.currentThread().getName());
                if (task != null) {
                    task.run();
                }
                logger.debug("Task finished by " + Thread.currentThread().getName());
            }
        }
    }
}
