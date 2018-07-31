package com.xiyou3g.xiyouhelper.threadLocal;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class DemoTask implements Runnable {

    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override
                protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    // Returns the current thread's unique ID, assigning it if necessary
    public int getThreadId() {
        return threadId.get();
    }

    // Returns the current thread's starting timestamp
    private static final ThreadLocal<Date> startDate =
            new ThreadLocal<Date>() {
                protected Date initialValue() {
                    return new Date();
                }
            };

    @Override
    public void run() {
        System.out.println("Starting Thread:  "  +getThreadId()+ " : " + startDate.get() +"n");
        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread Finished: "+ getThreadId()+" : " + startDate.get()+ "n");
    }

    public static void main(String[] args) {
        DemoTask demoTask = new DemoTask();
        new Thread(demoTask).start();
        new Thread(demoTask).start();
        new Thread(demoTask).start();
    }
}