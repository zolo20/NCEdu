package ru.ars.ncedu.task6;


import ru.ars.ncedu.task1.GenerationArrayInteger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MultithreadingGenerateArray1 implements Runnable {
    private BlockingQueue<Integer[]> queue;
    private int secondSleep;

    public MultithreadingGenerateArray1(BlockingQueue<Integer[]> queue, int secondSleep) {
        this.queue = queue;
        this.secondSleep = secondSleep;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(secondSleep);
                queue.put(GenerationArrayInteger.arrayGeneration(100_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
