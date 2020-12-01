package com.shenchu.app.framework;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 10:03
 */
public class ThreadManager {
    private static ThreadManager threadManager = new ThreadManager();

    public static ThreadManager getInstance() {
        return threadManager;
    }

    BlockingQueue workQueue;
    //处理中心
    private ExecutorService mThreadPoolExecutor;

    private ThreadManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3,
                3, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        //参数r，就是出问题丢出来的任务
                        addTask(r);
                    }
                });
        mThreadPoolExecutor.execute(coreTask);
    }


    //定义一个请求队列-阻塞
    private BlockingQueue<Runnable> mQueue = new LinkedBlockingDeque<>();

    //添加任务
    public void addTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        mQueue.add(runnable);
    }


    /**
     * 核心线程
     */
    private Runnable coreTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = mQueue.take();//如果没有任务，则在此处阻塞
                    mThreadPoolExecutor.execute(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
