package ru.ars.ncedu.pattern.FactoryMethod;

class MainFactoryMethod {
    public static void main(String[] args) {
        /*
        RedPen redPen = new RedPen();
        redPen.write();
        */

        CreatorPen creator = new CreatorBluePen();
        Pen pen = creator.createPen();
        pen.write();
    }
}
