package ru.ars.ncedu.pattern.AbstractFactory;

class MainAbstractFactory {
    public static void main(String[] args) {
        ProductsFactory creator = new BlueProductsFactory();
        Pencil pencil = creator.creatorPencil();
        Pen pen = creator.creatorPen();
        pencil.write();
        pen.write();
    }
}
