package ru.ars.ncedu.pattern.AbstractFactory;

class BluePen implements Pen {
    @Override
    public void write() {
        System.out.println("To write in blue pen");
    }
}
