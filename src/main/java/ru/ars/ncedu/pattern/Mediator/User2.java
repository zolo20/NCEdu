package ru.ars.ncedu.pattern.Mediator;

class User2 implements User {
    private Dispatcher dispatcher;

    public User2(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void sendMessage(String msg) {
        dispatcher.send(msg, this);
    }

    @Override
    public void getMassage(String msgOther) {
        System.out.println("User1:"+msgOther);
    }
}
