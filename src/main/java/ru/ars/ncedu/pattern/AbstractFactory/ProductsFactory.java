package ru.ars.ncedu.pattern.AbstractFactory;

interface ProductsFactory {
    Pen creatorPen();
    Pencil creatorPencil();
}
