package ru.ars.ncedu.pattern.FactoryMethod;

class RedPen implements Pen {
    @Override
    public void write() {
        System.out.println("To write red color");
    }
}
