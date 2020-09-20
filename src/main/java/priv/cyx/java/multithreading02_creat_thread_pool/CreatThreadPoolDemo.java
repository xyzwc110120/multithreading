package priv.cyx.java.multithreading02_creat_thread_pool;

import java.util.concurrent.*;

/**
 * 使用线程池来启动线程
 */
public class CreatThreadPoolDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建定长线程池，并设置线程数为 5
        ExecutorService fixedPool = Executors.newFixedThreadPool(5);
        // 将线程交给线程池
        RunnableThread runnableThread = new RunnableThread();
        for (int i = 0; i < 5; i++) {
            fixedPool.submit(runnableThread);
        }
        // 关闭资源
        fixedPool.shutdown();

        System.out.println("---------------------------------");

        // 创建可以延迟或定时的执行任务的定长线程池
        ScheduledExecutorService scheduledPoll = Executors.newScheduledThreadPool(5);
        // 将线程交给线程池
        CallableThread callableThread = new CallableThread();
        for (int i = 0; i < 5; i++) {
            /*
             * ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit)：
             *      delay：需要延迟的时间
             *      unit：延迟的时间的单位
             */
            ScheduledFuture<Integer> schedule = scheduledPoll.schedule(callableThread, 3, TimeUnit.SECONDS);
            System.out.println(schedule.get());
        }
        scheduledPoll.shutdown();
    }
}

class RunnableThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
        }
    }
}

class CallableThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 1;
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
            sum += i;
        }
        return sum;
    }
}
