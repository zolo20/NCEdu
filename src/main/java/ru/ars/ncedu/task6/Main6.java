package ru.ars.ncedu.task6;

import java.util.concurrent.*;

public class Main6 {
    public static void main(String... args) {
        BlockingQueue<Integer[]> queue = new ArrayBlockingQueue<>(Integer.parseInt(args[0]));
        ScheduledExecutorService executorService1 = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executorService2 = Executors.newSingleThreadScheduledExecutor();

        executorService1.scheduleAtFixedRate(new Thread
                (new MultithreadingGenerateArray1(queue)),
                Integer.valueOf(args[1]), Integer.valueOf(args[1]), TimeUnit.SECONDS);
        executorService2.scheduleAtFixedRate(new Thread
                (new MultithreadingSort1(queue)),
                Integer.valueOf(args[2]), Integer.valueOf(args[2]), TimeUnit.SECONDS);
    }
}
