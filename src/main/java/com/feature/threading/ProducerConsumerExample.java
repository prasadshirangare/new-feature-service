package com.feature.threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


class ProducerConsumerProcessor {

    private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void produce() {
        while (true) {
            try {
                queue.put(atomicInteger.incrementAndGet());
                System.out.println(Thread.currentThread().getName() + " : Produced : " + atomicInteger.get());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void consume() {
        while (true) {
            try {
                Integer take = queue.take();
                System.out.println(Thread.currentThread().getName() + " : Consumed : " + take);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}

public class ProducerConsumerExample {

    public static void main(String[] args) throws InterruptedException {

        ProducerConsumerProcessor processor = new ProducerConsumerProcessor();

        Thread t1 = new Thread(processor::produce);
        Thread t2 = new Thread(processor::consume);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Main ending");

    }

}
