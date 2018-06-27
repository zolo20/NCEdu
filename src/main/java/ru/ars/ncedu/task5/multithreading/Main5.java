package ru.ars.ncedu.task5.multithreading;

import java.util.concurrent.*;

public class Main5 {
    public static void main(String... args) {
        BlockingQueue<Integer[]> queue = new ArrayBlockingQueue<>(Integer.parseInt(args[0]));
        ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(args[1]));
        new Thread(new MultithreadingGenerateArray(queue)).start();
        executorService.submit(new MultithreadingSort(queue));
    }
}
