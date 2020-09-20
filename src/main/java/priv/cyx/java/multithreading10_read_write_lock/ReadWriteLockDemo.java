package priv.cyx.java.multithreading10_read_write_lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        ReadAndWrite readAndWrite = new ReadAndWrite();

        // 写线程
        for (int i = 0; i < 3; i++) {
            new Thread(() -> readAndWrite.setNum(666)).start();
        }
        // 读线程
        for (int i = 0; i < 50; i++) {
            new Thread(readAndWrite :: getNum).start();
        }
    }
}


class ReadAndWrite {

    private int num;

    // 创建读写锁对象
    ReadWriteLock lock = new ReentrantReadWriteLock();

    public void getNum() {
        // 读锁加锁
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "：read：" + num);
        } finally {
            // 释放锁资源
            lock.readLock().unlock();
        }
    }

    public void setNum(int num) {
        // 写锁加锁
        lock.writeLock().lock();
        try {
            this.num = num;
            System.out.println(Thread.currentThread().getName() + "：write：" + num);
        } finally {
            lock.writeLock().unlock();
        }
    }
}