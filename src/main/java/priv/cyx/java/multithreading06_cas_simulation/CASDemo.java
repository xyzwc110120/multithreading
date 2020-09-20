package priv.cyx.java.multithreading06_cas_simulation;

import java.util.Random;

public class CASDemo {

    public static void main(String[] args) {
        CASSimulation casSimulation = new CASSimulation();
        for (int i = 0; i < 10; i++) {
            new Thread(casSimulation).start();
        }
    }
}


class CASSimulation implements Runnable {

    private int oldValue = 0;

    /**
     * 比较方法
     *
     * @param expectValue 旧的预期值
     * @param newValue 要修改的新值
     * @return 返回内存值
     */
    public synchronized int compareAndSwap(int expectValue, int newValue) {
        // 获取内存值
        int oldValue = this.oldValue;
        // 若是旧的预期值等于内存值，则修改内存值为新值
        if (expectValue == oldValue) {
            this.oldValue = newValue;
            System.out.println(newValue);
        }
        return oldValue;
    }

    /**
     * 查看比较与交换是否成功
     */
    public boolean compareAndSet(int expectValue, int newValue) {
        return expectValue == compareAndSwap(expectValue, newValue);
    }

    @Override
    public void run() {
        System.out.println(
                Thread.currentThread().getName() +
                        " : " + compareAndSet(this.oldValue, new Random().nextInt(20)) +
                        " - " + this.oldValue
        );
    }
}