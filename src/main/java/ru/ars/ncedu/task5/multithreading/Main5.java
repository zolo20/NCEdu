package ru.ars.ncedu.task5.multithreading;

import ru.ars.ncedu.task1.GenerationArrayInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main5 {
    public static void main(String[] args) throws InterruptedException {
        final int CAPACITY = 5;
        List<Integer[]> queue = new ArrayList<>(CAPACITY);

        Thread genArr = new Thread(() -> {
            for (int i = 0; i < CAPACITY; i++) {
                queue.add(GenerationArrayInteger.arrayGeneration(10));
            }
        });
        genArr.start();
        genArr.join();

        System.out.println(queue);

    }
}
