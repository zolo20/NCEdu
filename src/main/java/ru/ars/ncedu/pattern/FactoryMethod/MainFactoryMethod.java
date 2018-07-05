package ru.ars.ncedu.pattern.FactoryMethod;

class MainFactoryMethod {
    public static void main(String[] args) {
        CreatorPen creator = new CreatorBluePen();
        Pen pen = creator.createPen();
        pen.write();
    }
}
