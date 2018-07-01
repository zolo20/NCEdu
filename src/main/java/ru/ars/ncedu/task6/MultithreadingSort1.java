package ru.ars.ncedu.task6;

import ru.ars.ncedu.task1.QuickSort;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MultithreadingSort1 implements Runnable {
    private BlockingQueue<Integer[]> queue;
    private int secondSleep;

    public MultithreadingSort1(BlockingQueue<Integer[]> queue, int secondSleep) {
        this.queue = queue;
        this.secondSleep = secondSleep;
    }

    @Override
    public void run() {
        while (true) {
            try {
                QuickSort.sort(queue.take());
                System.out.println("Complected");
                TimeUnit.SECONDS.sleep(secondSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
