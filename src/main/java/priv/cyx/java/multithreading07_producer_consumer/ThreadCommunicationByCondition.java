package priv.cyx.java.multithreading07_producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 Condition 控制线程通信示例：lock + condition + await + signal
 */
public class ThreadCommunicationByCondition {

    public static void main(String[] args) {
        Goods goods = new Goods();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(new ConditionProducer(goods, lock, condition), "生产者 A").start();
        new Thread(new ConditionProducer(goods, lock, condition), "生产者 B").start();
        new Thread(new ConditionConsumer(goods, lock, condition), "消费者 A").start();
        new Thread(new ConditionConsumer(goods, lock, condition), "消费者 B").start();
    }
}

class ConditionProducer implements Runnable {

    Goods goods;
    Lock lock;
    Condition condition;

    public ConditionProducer(Goods goods, Lock lock, Condition condition) {
        this.goods = goods;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            produce();
        }
    }

    private void produce() {
        lock.lock();
        try {
            while (goods.getNum() > 5) {
                condition.signalAll();
                System.out.println(Thread.currentThread().getName() + "：" + "仓库已满，停止生产");
                condition.await();
            }
            goods.setNum(goods.getNum() + 1);
            System.out.println(Thread.currentThread().getName() + "：" + goods.getNum());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class ConditionConsumer implements Runnable {

    Goods goods;
    Lock lock;
    Condition condition;

    public ConditionConsumer(Goods goods, Lock lock, Condition condition) {
        this.goods = goods;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            consume();
        }
    }

    private void consume() {
        lock.lock();
        try {
            while (goods.getNum() < 1) {
                condition.signalAll();
                System.out.println(Thread.currentThread().getName() + "：" + "商品数量不足，停止消费");
                condition.await();
            }
            goods.setNum(goods.getNum() - 1);
            System.out.println(Thread.currentThread().getName() + "：" + goods.getNum());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}