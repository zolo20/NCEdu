package ru.ars.ncedu.task6;


import ru.ars.ncedu.task1.GenerationArrayInteger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MultithreadingGenerateArray1 implements Runnable {
    private BlockingQueue<Integer[]> queue;


    public MultithreadingGenerateArray1(BlockingQueue<Integer[]> queue) {
        this.queue = queue;

    }

    @Override
    public void run() {
        try {
            queue.put(GenerationArrayInteger.arrayGeneration(100_000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
