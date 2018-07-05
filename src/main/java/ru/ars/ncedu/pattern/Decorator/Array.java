package ru.ars.ncedu.pattern.Decorator;

interface Array<T> {
    T get(int index);
    T set(int index, T obj);
}
