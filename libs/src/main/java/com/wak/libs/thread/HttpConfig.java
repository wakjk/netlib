package com.wak.libs.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by JiangKe on 2017/5/5.
 */

public class HttpConfig {
    // 默认核心池大小
    private static final int DEFAULT_CORE_SIZE = 4;
    // 最大线程数
    private static final int DEFAULT_MAX_SIZE = 8;
    // 池中空余线程存活时间
    private static final long DEFAULT_KEEP_ALIVE_TIME = 30;
    // 时间单位
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    // 线程池阻塞队列(默认队列长度为50)
    private static final int BLOCKING_QUEUE_SIZE = 16;
    private static BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_SIZE);
    // 默认初始化
    private int corePoolZie = DEFAULT_CORE_SIZE;
    private int maxPoolSize = DEFAULT_MAX_SIZE;
    private long keepAliveTime = DEFAULT_KEEP_ALIVE_TIME;
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;

    private HttpConfig() {
    }

    public static HttpConfig getInstance() {
       return HttpConfigHolder.Instance;
    }

    private static class HttpConfigHolder {
        private final static HttpConfig Instance = new HttpConfig();
    }

    public HttpConfig corePoolZie(int corePoolZie) {
        this.corePoolZie = corePoolZie;
        return this;
    }

    public HttpConfig maxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public HttpConfig keepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public HttpConfig timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public HttpConfig blockingQueue(BlockingQueue<Runnable> blockingQueue) {
        this.blockingQueue = blockingQueue;
        return this;
    }


    public int getDefaultCoreSize() {
        return DEFAULT_CORE_SIZE;
    }

    public BlockingQueue<Runnable> getBlockingQueue() {
        return blockingQueue;
    }

    public int getCorePoolZie() {
        return corePoolZie;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }


}
