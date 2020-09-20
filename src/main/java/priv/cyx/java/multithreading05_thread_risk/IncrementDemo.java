package priv.cyx.java.multithreading05_thread_risk;

public class IncrementDemo {

    public static void main(String[] args) {
        IncrementThread thread = new IncrementThread();
        for (int i = 0; i < 10; i++) {
            new Thread(thread).start();
        }
    }
}

class IncrementThread implements Runnable {

    private int num = 0;

    public int getNum() {
        return num++;
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