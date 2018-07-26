package com.xiyou3g.xiyouhelper;

import java.util.concurrent.TimeUnit;

/**
 * @author mengchen
 * @time 18-7-25 下午5:03
 */
public class TestThread {
    public ThreadLocal<Integer> a = new ThreadLocal();

    public void test(String test, int a) throws InterruptedException {
        this.a.set(a);
        System.out.println("执行test方法" + test);
        TimeUnit.SECONDS.sleep(20);
        System.out.println(this.a.get());
    }

    public static void main(String[] args) throws InterruptedException {
        TestThread testThread = new TestThread();
        testThread.a.set(2);
        AA a = new AA(testThread, "aaaa", 1);
        AA b = new AA(testThread, "bbbb", 100);
        a.start();
        b.start();
        while (true) {
            System.out.println(testThread.a.get());
        }
    }
}

class AA extends Thread {

    TestThread t;
    int integer;
    String str;

    public AA(TestThread t, String str, int integer) throws InterruptedException {
        this.t = t;
        this.str = str;
        this.integer = integer;
    }

    @Override
    public void run() {
        try {
            if (integer == 1) {
                TimeUnit.SECONDS.sleep(10);
                t.a.set(integer);
                TimeUnit.SECONDS.sleep(50);
            } else {
                TimeUnit.SECONDS.sleep(5);
            }
            t.test(str, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
