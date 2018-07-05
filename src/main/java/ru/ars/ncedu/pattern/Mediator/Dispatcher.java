package ru.ars.ncedu.pattern.Mediator;

interface Dispatcher {
    void send(String msg, User plane);
}
