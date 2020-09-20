package priv.cyx.java.multithreading04_volatile;

public class VolatileDemo {

    public static void main(String[] args) {

        VolatileThread thread = new VolatileThread();
        new Thread(thread).start();
        while (true) {
            System.out.println("执行顺序 1");
            if (thread.isFlag()) {
                System.out.println("执行顺序 2");
                System.out.println("循环结束");
                break;
            }
        }
    }
}

class VolatileThread implements Runnable {

    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    @Override
    public void run() {
        System.out.println("执行顺序 3");
        flag = true;
        System.out.println("执行顺序 4");
    }
}
