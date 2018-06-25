package ru.ars.ncedu.task5.pingpong;


public class PingPongMain {
    private static final Object monitor = new Object();

    public static void main(String[] args) {
        new Ping(monitor).start();
        new Pong(monitor).start();
    }
}
