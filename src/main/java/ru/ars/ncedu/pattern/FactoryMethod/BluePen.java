package ru.ars.ncedu.pattern.FactoryMethod;

class BluePen implements Pen {
    @Override
    public void write() {
        System.out.println("To write blue color");
    }
}
