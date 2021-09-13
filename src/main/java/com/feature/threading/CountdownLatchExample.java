package com.feature.threading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountdownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        System.out.println("Latch not released....");
        latch.await();
        System.out.println("Latch released....");

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        executorService.shutdownNow();

    }
}
