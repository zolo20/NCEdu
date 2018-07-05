package ru.ars.ncedu.pattern.AbstractFactory;

public class RedProductsFactory implements ProductsFactory {
    @Override
    public Pen creatorPen() {
        return new RedPen();
    }

    @Override
    public Pencil creatorPencil() {
        return new RedPencil();
    }
}
