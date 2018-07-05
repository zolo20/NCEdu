package ru.ars.ncedu.pattern.AbstractFactory;

class BluePencil implements Pencil{
    @Override
    public void write() {
        System.out.println("To write in blue pencil");
    }
}
