package priv.cyx.java.multithreading08_interview_question;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍。
 * 要求输出的结果必须按照顺序显示。如：ABCABCABC...... 依次递归。
 *
 * 方式二，使用 Condition，对不同线程池中的线程分别唤醒
 */
public class InterviewQuestion02 {

    public static void main(String[] args) {
        Counter counter = new Counter();
        Lock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        LockA a = new LockA(counter, lock, conditionA, conditionB);
        LockB b = new LockB(counter, lock, conditionB, conditionC);
        LockC c = new LockC(counter, lock, conditionC, conditionA);
        new Thread(a).start();
        new Thread(b).start();
        new Thread(c).start();

    }
}

class LockA implements Runnable {

    Counter counter;
    Lock lock;
    // 需要睡眠的线程池
    Condition aWaitCondition;
    // 需要唤醒的线程池
    Condition signalCondition;

    public LockA(Counter counter, Lock lock, Condition aWaitCondition, Condition signalCondition) {
        this.counter = counter;
        this.lock = lock;
        this.aWaitCondition = aWaitCondition;
        this.signalCondition = signalCondition;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            printA();
        }
    }

    private void printA() {
        lock.lock();
        try {
            while (counter.getCounter() != 0) {
                aWaitCondition.await();
            }
            System.out.println(Thread.currentThread().getName() + "：A");
            // 修改计数器，唤醒 B 线程
            counter.setCounter(1);
            signalCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class LockB implements Runnable {

    Counter counter;
    Lock lock;
    // 需要睡眠的线程池
    Condition aWaitCondition;
    // 需要唤醒的线程池
    Condition signalCondition;

    public LockB(Counter counter, Lock lock, Condition aWaitCondition, Condition signalCondition) {
        this.counter = counter;
        this.lock = lock;
        this.aWaitCondition = aWaitCondition;
        this.signalCondition = signalCondition;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            printB();
        }
    }

    private void printB() {
        lock.lock();
        try {
            while (counter.getCounter() != 1) {
                aWaitCondition.await();
            }
            System.out.println(Thread.currentThread().getName() + "：B");
            // 修改计数器，唤醒 B 线程
            counter.setCounter(2);
            signalCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class LockC implements Runnable {

    Counter counter;
    Lock lock;
    // 需要睡眠的线程池
    Condition aWaitCondition;
    // 需要唤醒的线程池
    Condition signalCondition;

    public LockC(Counter counter, Lock lock, Condition aWaitCondition, Condition signalCondition) {
        this.counter = counter;
        this.lock = lock;
        this.aWaitCondition = aWaitCondition;
        this.signalCondition = signalCondition;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            printC();
        }
    }

    private void printC() {
        lock.lock();
        try {
            while (counter.getCounter() != 2) {
                aWaitCondition.await();
            }
            System.out.println(Thread.currentThread().getName() + "：C");
            // 修改计数器，唤醒 B 线程
            counter.setCounter(0);
            signalCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
