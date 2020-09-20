package priv.cyx.java.multithreading01_creat_thread;

import java.util.concurrent.*;

/**
 * 创建线程的 3 种方式
 */
public class CreatThreadDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 方式一启动线程
        RunnableDemo runnableDemo = new RunnableDemo();
        new Thread(runnableDemo).start();

        // 方式二启动线程
        new ThreadDemo().start();

        // 方式三启动线程
        FutureTask<Integer> futureTask = new FutureTask<>(new CallableDemo());
        // 通过查看 FutureTask 类的源码，发先 FutureTask 实现了 Runnable 接口
        new Thread(futureTask).start();
        // 通过 FutureTask 对象的 get() 方法获取 call 方法的返回值
        System.out.println(futureTask.get());
    }
}

/**
 * 方式一：实现 Runnable 接口
 */
class RunnableDemo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "-" + i);
        }
    }
}

/**
 * 方式二：继承 Thread 类
 */
class ThreadDemo extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "-" + i);
        }
    }
}

/**
 * 方式三：实现 Callable 接口
 *      Callable 接口可定义泛型，并且该泛型会作为抽象方法的返回类型
 */
class CallableDemo implements Callable<Integer> {

    @Override
    public Integer call() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += i;
        }
        return sum;
    }
}