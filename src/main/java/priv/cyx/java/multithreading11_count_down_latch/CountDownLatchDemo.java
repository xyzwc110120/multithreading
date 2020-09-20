package priv.cyx.java.multithreading11_count_down_latch;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟公交车，每位乘客（线程）上车后，可用作为 -1，直到为 0，司机开车出发
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        int seats = 30;
        // 实例化一个倒计数器，指定计数个数
        CountDownLatch countDownLatch = new CountDownLatch(seats);
        Bus bus = new Bus(seats, countDownLatch);

        for (int i = 0; i < seats; i++) {
            new Thread(bus).start();
            Thread.sleep((long) (Math.random() * 100));
        }
        // 等待，当计数器减到 0 时，所有线程并行
        countDownLatch.await();
        System.out.println("车上满了，开车！");
    }
}


class Bus implements Runnable {

    // 车上座位数
    private int seats;

    // 倒计时器
    private CountDownLatch countDownLatch;

    public Bus(int seats, CountDownLatch countDownLatch) {
        this.seats = seats;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        seats -= 1;
        System.out.println("剩余座位：" + seats);
        // 计数减一
        countDownLatch.countDown();
    }
}