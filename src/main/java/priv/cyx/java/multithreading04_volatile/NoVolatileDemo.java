package priv.cyx.java.multithreading04_volatile;

public class NoVolatileDemo {

    public static void main(String[] args) {
        RunnableThread thread = new RunnableThread();
        new Thread(thread).start();

        while (true) {
            if (thread.isFlag()) {
                System.out.println("结束循环。");
                break;
            }
        }
    }
}

class RunnableThread implements Runnable {

    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    @Override
    public void run() {
        // 让线程睡眠 0.2 秒，使问题更容易出现
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;
        System.out.println(Thread.currentThread().getName() + "：" + flag);
    }
}
