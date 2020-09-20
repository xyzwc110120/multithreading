package priv.cyx.java.multithreading05_thread_risk;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {

    public static void main(String[] args) {
        AtomicIntegerThread thread = new AtomicIntegerThread();
        for (int i = 0; i < 10; i++) {
            new Thread(thread).start();
        }
    }
}


class AtomicIntegerThread implements Runnable {

    private AtomicInteger num = new AtomicInteger();

    public int getNum() {
        /*
         * int getAndIncrement()：
         *      先获取再递增，类似于 i++
         *
         * int incrementAndGet()：
         *      先递增后获取，类似于 ++i
         */
        return num.getAndIncrement();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(getNum());
    }
}