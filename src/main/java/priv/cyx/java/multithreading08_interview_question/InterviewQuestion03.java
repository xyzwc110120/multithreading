package priv.cyx.java.multithreading08_interview_question;


/**
 * 现在有 T1、T2、T3 三个线程，怎样保证 T2 在 T1 执行完后执行，T3 在 T2 执行完后执行？
 *
 * 使用联合线程（使用 Thread 类中的 join() 方法）
 */
public class InterviewQuestion03 {

    public static void main(String[] args) throws InterruptedException {
        // 方式一
        /*
            Thread t1 = new Thread(new T1());
            Thread t2 = new Thread(new T2());
            Thread t3 = new Thread(new T3());
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        */

        // 方式二
        T1 t1 = new T1();
        T2 t2 = new T2();
        T3 t3 = new T3();
        Thread t1t = new Thread(t1);
        Thread t2t = new Thread(t2);
        Thread t3t = new Thread(t3);

        // T3 线程要等 T2 线程执行完再执行
        t3.setJoinThread(t2t);
        // T2 线程要等 T1 线程执行完再执行
        t2.setJoinThread(t1t);
        t3t.start();
        t2t.start();
        t1t.start();
    }
}

class T1 implements Runnable {

    // 需要联合的线程
    private Thread joinThread;

    public void setJoinThread(Thread joinThread) {
        this.joinThread = joinThread;
    }

    @Override
    public void run() {
        if (joinThread != null) {
            try {
                joinThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "：T1");
    }
}

class T2 implements Runnable {

    // 需要联合的线程
    private Thread joinThread;

    public void setJoinThread(Thread joinThread) {
        this.joinThread = joinThread;
    }

    @Override
    public void run() {
        if (joinThread != null) {
            try {
                joinThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "：T2");
    }
}

class T3 implements Runnable {


    // 需要联合的线程
    private Thread joinThread;

    public void setJoinThread(Thread joinThread) {
        this.joinThread = joinThread;
    }

    @Override
    public void run() {
        if (joinThread != null) {
            try {
                joinThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "：T3");
    }
}