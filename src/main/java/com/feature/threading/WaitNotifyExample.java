package com.feature.threading;

import java.util.*;
import java.util.concurrent.TimeUnit;

class Processor {

    List<Integer> integerList = new LinkedList<>();
    int capacity = 10;

    public void produce() {
        Random random = new Random();

        while (true) {
            synchronized (this) {
                if (integerList.isEmpty() & integerList.size() < capacity) {
                    final int element = random.nextInt(10);
                    integerList.add(element);
                    System.out.println(Thread.currentThread().getName() + " Added :" + element);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void consume() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            synchronized (this) {
                if (!integerList.isEmpty()) {
                    final Integer element = integerList.remove(integerList.size() - 1);
                    System.out.println(Thread.currentThread().getName() + " Polled: " + element);
                } else notify();
            }
        }
    }
}

public class WaitNotifyExample {

    public static void main(String[] args) {

        Processor processor = new Processor();
        Thread t1 = new Thread(processor::produce);
        Thread t2 = new Thread(processor::consume);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End");

    }
}
