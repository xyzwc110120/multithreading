package priv.cyx.java.multithreading08_interview_question;

import java.util.concurrent.locks.LockSupport;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍。
 * 要求输出的结果必须按照顺序显示。如：ABCABCABC...... 依次递归。
 *
 * 方式一，使用 LockSupport 唤醒指定线程
 */
public class InterviewQuestion01 {

    public static void main(String[] args) {
        Counter counter = new Counter();
        LockSupportA a = new LockSupportA(counter);
        LockSupportB b = new LockSupportB(counter);
        LockSupportC c = new LockSupportC(counter);
        Thread aThread = new Thread(a);
        Thread bThread = new Thread(b);
        Thread cThread = new Thread(c);
        // 执行要唤醒的线程
        a.setWakeupThread(bThread);
        b.setWakeupThread(cThread);
        c.setWakeupThread(aThread);
        aThread.start();
        bThread.start();
        cThread.start();
    }
}

class LockSupportA implements Runnable {

    // 计数器，用来判断现在是否该 A 线程执行
    Counter counter;
    // 需要唤醒的线程
    Thread wakeupThread;

    public LockSupportA(Counter counter) {
        this.counter = counter;
    }

    public void setWakeupThread(Thread wakeupThread) {
        this.wakeupThread = wakeupThread;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            printA();
        }
    }

    private synchronized void printA() {
        while (counter.getCounter() != 0) {
            LockSupport.park();
        }
        System.out.println(Thread.currentThread().getName() + "：A");
        // 修改计数器，唤醒 B 线程
        counter.setCounter(1);
        // 唤醒线程 B
        LockSupport.unpark(wakeupThread);
    }
}

class LockSupportB implements Runnable {

    // 计数器，用来判断现在是否该 A 线程执行
    Counter counter;
    // 需要唤醒的线程
    Thread wakeupThread;

    public LockSupportB(Counter counter) {
        this.counter = counter;
    }

    public void setWakeupThread(Thread wakeupThread) {
        this.wakeupThread = wakeupThread;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            printB();
        }
    }

    private synchronized void printB() {
        while (counter.getCounter() != 1) {
            LockSupport.park();
        }
        System.out.println(Thread.currentThread().getName() + "：B");
        // 修改计数器，唤醒 C 线程
        counter.setCounter(2);
        // 唤醒线程 C
        LockSupport.unpark(wakeupThread);
    }
}

class LockSupportC implements Runnable {

    // 计数器，用来判断现在是否该 A 线程执行
    Counter counter;
    // 需要唤醒的线程
    Thread wakeupThread;

    public LockSupportC(Counter counter) {
        this.counter = counter;
    }

    public void setWakeupThread(Thread wakeupThread) {
        this.wakeupThread = wakeupThread;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            printC();
        }
    }

    private synchronized void printC() {
        while (counter.getCounter() != 2) {
            LockSupport.park();
        }
        System.out.println(Thread.currentThread().getName() + "：C");
        // 修改计数器，唤醒 A 线程
        counter.setCounter(0);
        // 唤醒线程 A
        LockSupport.unpark(wakeupThread);
    }
}