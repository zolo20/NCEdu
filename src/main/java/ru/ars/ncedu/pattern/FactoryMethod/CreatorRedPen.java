package ru.ars.ncedu.pattern.FactoryMethod;

class CreatorRedPen implements CreatorPen {
    @Override
    public Pen createPen() {
        return new RedPen();
    }
}
