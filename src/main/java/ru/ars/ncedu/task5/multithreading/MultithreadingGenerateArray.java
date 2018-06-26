package ru.ars.ncedu.task5.multithreading;

import ru.ars.ncedu.task1.GenerationArrayInteger;

public class MultithreadingGenerateArray extends Thread {

    @Override
    public void run() {
        GenerationArrayInteger.arrayGeneration(100_000);
    }
}
