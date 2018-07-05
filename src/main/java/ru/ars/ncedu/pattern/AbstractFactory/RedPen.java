package ru.ars.ncedu.pattern.AbstractFactory;

public class RedPen implements Pen {
    @Override
    public void write() {
        System.out.println("To write in red pen");
    }
}
