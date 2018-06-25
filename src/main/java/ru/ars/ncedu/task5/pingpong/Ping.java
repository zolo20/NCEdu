package ru.ars.ncedu.task5.pingpong;

import java.util.concurrent.TimeUnit;

public class Ping extends Thread {

    final Object object;

    public Ping(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (object) {
                System.out.println("Ping");
                object.notifyAll();
                try {
                    object.wait();
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("---------------");

            }
        }
    }
}
