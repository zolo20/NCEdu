package ru.ars.ncedu.task5.pingpong;

public class PingPongMain {
    private static final Object monitor = new Object();
    private static String state = "Pong";

    public static void main(String[] args) {
        for (int i = 0;i < 10;i++) {
            Thread ping = new Thread(() -> {
                synchronized (monitor) {
                    try {
                        while (!state.equals("Pong")) monitor.wait();
                        state = "Ping";
                        System.out.println(state);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    monitor.notify();

                }
            });

            Thread pong = new Thread(() -> {
               synchronized (monitor) {
                   try {
                       while (!state.equals("Ping")) monitor.wait();
                       state = "Pong";
                       System.out.println(state);
                       System.out.println("--------");
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   monitor.notify();
               }
            });
            ping.start();
            pong.start();
        }
    }
}
