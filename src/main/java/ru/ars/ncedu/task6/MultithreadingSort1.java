package ru.ars.ncedu.task6;

import ru.ars.ncedu.task1.QuickSort;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MultithreadingSort1 implements Runnable {
    private BlockingQueue<Integer[]> queue;

    public MultithreadingSort1(BlockingQueue<Integer[]> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            QuickSort.sort(queue.take());
            System.out.println("Complected");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
