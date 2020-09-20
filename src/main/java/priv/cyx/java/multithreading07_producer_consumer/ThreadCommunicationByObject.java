package priv.cyx.java.multithreading07_producer_consumer;

import javax.xml.transform.Source;

/**
 * 使用传统线程通讯方式：synchronized + wait + notify / notifyAll
 */
public class ThreadCommunicationByObject {

    public static void main(String[] args) throws InterruptedException {
        ObjectGoodsOperate operate = new ObjectGoodsOperate(new Goods());
        new Thread(new ObjectProducer(operate), "生产者 A").start();
        new Thread(new ObjectProducer(operate), "生产者 B").start();
        new Thread(new ObjectConsumer(operate), "消费者 A").start();
        new Thread(new ObjectConsumer(operate), "消费者 B").start();
    }
}

class ObjectGoodsOperate {

    Goods goods;

    public ObjectGoodsOperate(Goods goods) {
        this.goods = goods;
    }

    public synchronized void produce() {
        // 若商品数量大于 5 则停止生产，并唤醒消费者
        while (goods.getNum() > 5) {
            try {
                notifyAll();
                System.out.println(Thread.currentThread().getName() + "：" + "仓库已满，停止生产");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        goods.setNum(goods.getNum() + 1);
        System.out.println(Thread.currentThread().getName() + "：" + goods.getNum());
    }

    public synchronized void consume() {
        while (goods.getNum() < 1) {       // 若商品数量小于 1，则停止消费
            try {
                notifyAll();
                System.out.println(Thread.currentThread().getName() + "：" + "商品数量不足，停止消费");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        goods.setNum(goods.getNum() - 1);
        System.out.println(Thread.currentThread().getName() + "：" + goods.getNum());
    }
}

class ObjectProducer implements Runnable {
    
    ObjectGoodsOperate operate;
    
    public ObjectProducer(ObjectGoodsOperate operate) {
        this.operate = operate;
    }

    @Override
    public void run() {
        for (int i = 0; i< 20; i++) {
            operate.produce();
        }
    }
}

class ObjectConsumer implements Runnable {

    ObjectGoodsOperate operate;

    public ObjectConsumer(ObjectGoodsOperate operate) {
        this.operate = operate;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            operate.consume();
        }
    }
}