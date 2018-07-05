package ru.ars.ncedu.pattern.Mediator;

public class User1 implements User {
    private Dispatcher dispatcher;

    public User1(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void sendMessage(String msg) {
        dispatcher.send(msg, this);
    }

    @Override
    public void getMassage(String msgOther) {
        System.out.println("User2:" + msgOther);
    }
}
