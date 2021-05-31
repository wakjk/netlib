package com.wak.libs.thread;



import com.wak.libs.utils.OKHttpUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JiangKe on 2017/5/4.
 */

public class ThreadManager {

    // 封装的线程池
    private ThreadPoolExecutor pool;

    public static ThreadManager getInstance() {
        return ThreadManagerHolder.Instance;
    }

    private static class ThreadManagerHolder {
        private final static ThreadManager Instance = new ThreadManager();
    }

    private ThreadManager() {
        init();
    }

    /**
     * 根据配置信息初始化线程池
     */
    private synchronized void init() {
        if (null == pool) {
            HttpConfig config = HttpConfig.getInstance();
            pool = new ThreadPoolExecutor(config.getCorePoolZie(),
                    config.getMaxPoolSize(), config.getKeepAliveTime(),
                    config.getTimeUnit(), config.getBlockingQueue(), new ThreadPoolExecutor.DiscardOldestPolicy());
        }
    }

    /**
     * 执行任务
     *
     * @param r
     */
    public synchronized void execute(final Runnable r) {
        if (r != null) {
            try {
                pool.execute(r);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清空阻塞队列
     */
    public synchronized void removeBlockTask() {
        pool.getQueue().clear();
    }

    /**
     * 从阻塞队列中删除指定任务
     *
     * @param obj
     * @return
     */
    public synchronized boolean removeTaskFromQueue(final Object obj) {
        if (!pool.getQueue().contains(obj)) {
            return false;
        }

        pool.getQueue().remove(obj);
        return true;
    }

    /**
     * 获取阻塞队列
     *
     * @return
     */
    public synchronized BlockingQueue<Runnable> getQuene() {
        return pool.getQueue();
    }

    /**
     * 关闭，并等待任务执行完成，不接受新任务
     */
    public synchronized void shutdown() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public synchronized void shutdownRightnow() {
        if (null != pool) {
            pool.shutdownNow();
            try {
                // 设置超时极短，强制关闭所有任务
                pool.awaitTermination(1,
                        TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelCall(String tag) {
        OKHttpUtils.cancalReq(tag);
    }

}
