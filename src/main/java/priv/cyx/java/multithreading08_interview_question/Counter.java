package priv.cyx.java.multithreading08_interview_question;

public class Counter {

    // 计数器，用来记录该哪个线程执行了
    int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
