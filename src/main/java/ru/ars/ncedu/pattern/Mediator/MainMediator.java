package ru.ars.ncedu.pattern.Mediator;

public class MainMediator {
    public static void main(String[] args) {
        DispatcherVoice dispatcher = new DispatcherVoice();
        User user1 = new User1(dispatcher);
        User user2 = new User2(dispatcher);
        dispatcher.setUser(user2);
        user1.sendMessage("Hello");
        dispatcher.setUser(user1);
        user2.sendMessage("Bye");
    }
}
