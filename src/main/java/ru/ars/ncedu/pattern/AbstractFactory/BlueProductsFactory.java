package ru.ars.ncedu.pattern.AbstractFactory;

public class BlueProductsFactory implements ProductsFactory {
    @Override
    public Pen creatorPen() {
        return new BluePen();
    }

    @Override
    public Pencil creatorPencil() {
        return new BluePencil();
    }
}
