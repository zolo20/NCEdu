package ru.ars.ncedu.task5.pingpong;


public class PingPongMain {
    private static  String state = "Pong";

    public static void main(String[] args) throws InterruptedException {
        for (;;) {
            Thread ping = new Thread(() -> {
                if (state.equals("Pong")) {
                    state = "Ping";
                    System.out.println(1+state);
                }
            });

            Thread pong = new Thread(() -> {
               if (!ping.isAlive()) {
                   state = "Pong";
                   System.out.println(2+state);
                   System.out.println("-------");
               }
            });
            ping.start();
            pong.start();
            pong.join();
        }
    }
}
