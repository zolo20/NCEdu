package ru.ars.ncedu.task5.pingpong;

import java.util.concurrent.TimeUnit;

public class Pong extends Thread {

    final Object object;

    public Pong(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (object) {
                System.out.println("Pong");
                object.notify();
                try {
                    object.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

