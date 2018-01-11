package com.daydvr.store.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by LoSyc on 2017/12/14.
 *
 * 线程池工具类
 * 用于手动创建线程池，避免大量的重复的创建的线程造成的消耗
 * 需要引用Google 库：com.google.guava:guava:xx.x-android
 * https://github.com/google/guava
 *
 */
public class ThreadPoolUtil {

    private static final String TAG = "daydvr.ThreadPoolUtil";

    /**
     * @param singleThreadPool 需要被创建或者初始化的线程池对象
     * @return 完成初始化或者创建线程池对象
     *
     * ThreadPoolExecutor（...）        线程池
     * param :
     * corePoolSize                             核心线程数(可以执行任务的的线程数)
     * maximum                                  最大线程数(阻塞队列已满时，可最多创建的线程数)
     * keepAliveTime                           空闲线程的存活时间（corePool不受限制）
     * TimeUnit                                    时间单位
     * BlockingQueue                          阻塞队列(线程数等于核心线程数，任务入队，队列已满则创建线程直至最大线程数，来执行任务)
     *          常用以下2种：
     *         SynchronousQueue             不缓存任务，maximum不起作用，入队操作会阻塞，出队操作唤醒
     *         LinkedBlockingQueue          链表队列
     * ThreadFactory                            线程工厂
     *RejectedExecutionHandler       拒绝策略（阻塞队列满，最大线程数满）
     *       AbortPolicy                         拒绝任务，并抛出异常
     *       CallerRunsPolicy                交给向线程池提交任务的线程执行（一般情况是主线程）
     *       DiscardOldestPolicy          抛弃队列中最旧的任务，然后入队（最先提交而没有得到执行的任务）
     *       DiscardPolicy                     拒绝任务，不抛异常
     */
    public static ExecutorService getSingleThreadPool(ExecutorService singleThreadPool) {
        if (singleThreadPool == null) {
            singleThreadPool = new ThreadPoolExecutor(1, 1, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024)
                    , new ThreadFactoryBuilder().build(), new ThreadPoolExecutor.AbortPolicy());
        }
        return singleThreadPool;
    }

    public static ExecutorService getMuiltThreadPool(ExecutorService mulitThreadPool) {
        if (mulitThreadPool == null) {
            mulitThreadPool = new ThreadPoolExecutor(0, 10, 10L,
                    TimeUnit.SECONDS, new SynchronousQueue<Runnable>()
                    , new ThreadFactoryBuilder().build(), new ThreadPoolExecutor.AbortPolicy());
        }
        return mulitThreadPool;
    }

    public static void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                    Logger.d(TAG, "Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
