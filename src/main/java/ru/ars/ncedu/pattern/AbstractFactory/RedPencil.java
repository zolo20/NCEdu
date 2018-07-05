package ru.ars.ncedu.pattern.AbstractFactory;

public class RedPencil implements Pencil {
    @Override
    public void write() {
        System.out.println("To write in red pencil");
    }
}
