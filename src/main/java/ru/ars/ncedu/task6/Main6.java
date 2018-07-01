package ru.ars.ncedu.task6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main6 {
    public static void main(String... args) {
        BlockingQueue<Integer[]> queue = new ArrayBlockingQueue<>(Integer.parseInt(args[0]));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        new Thread(new MultithreadingGenerateArray1(queue, Integer.valueOf(args[1]))).start();
        executorService.submit(new MultithreadingSort1(queue, Integer.valueOf(args[2])));
    }
}
