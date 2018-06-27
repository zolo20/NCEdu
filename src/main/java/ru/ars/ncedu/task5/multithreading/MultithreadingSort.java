package ru.ars.ncedu.task5.multithreading;

import ru.ars.ncedu.task1.QuickSort;

import java.util.concurrent.BlockingQueue;

public class MultithreadingSort implements Runnable {
    private BlockingQueue<Integer[]> queue;

    public MultithreadingSort(BlockingQueue<Integer[]> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                QuickSort.sort(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Complected");
        }
    }
}
