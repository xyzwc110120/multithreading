package priv.cyx.java.multithreading09_java_util_concurrent;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        /*
            // 因为 ArrayList 为非线程安全的，所以会引起并发修改异常：ConcurrentModificationException
            ArrayListTest arrayListTest = new ArrayListTest();
            for (int i = 0; i < 2; i++) {
                new Thread(arrayListTest).start();
            }
        */

        CopyWriteArrayListTest listTest = new CopyWriteArrayListTest();
        for (int i = 0; i < 2; i++) {
            new Thread(listTest).start();
        }
    }

}


class CopyWriteArrayListTest implements Runnable {

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("A");
        list.add("B");
        list.add("C");
    }

    @Override
    public void run() {
        for (String s : list) {
            System.out.println(Thread.currentThread().getName() + "：" + s);
            list.add("D");
            System.out.println(Thread.currentThread().getName() + "：" + list);
        }
    }
}


class ArrayListTest implements Runnable {

    private static ArrayList<String> list = new ArrayList<>();

    static {
        list.add("A");
        list.add("B");
        list.add("C");
    }

    @Override
    public void run() {
        for (String s : list) {
            System.out.println(s);
            list.add("D");
        }
    }
}
