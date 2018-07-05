package ru.ars.ncedu.pattern.FactoryMethod;

class CreatorBluePen implements CreatorPen {
    @Override
    public Pen createPen() {
        return new BluePen();
    }
}
