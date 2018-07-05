package ru.ars.ncedu.pattern.Mediator;

public class DispatcherVoice implements Dispatcher{
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void send(String msg, User user) {
        this.user.getMassage(msg);
    }
}
