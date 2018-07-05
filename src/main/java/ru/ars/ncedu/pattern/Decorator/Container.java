package ru.ars.ncedu.pattern.Decorator;

class Container<T> implements SortDESC<T>{
    private T[] array;

    public Container(T[] array) {
        this.array = array;
    }

    @Override
    public void sortDESC() {
        System.out.println("sorted DESC");
    }

    @Override
    public void sortASC() {
        System.out.println("sorted ASC");
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public T set(int index, T obj) {
        T oldValue = array[index];
        array[index] = obj;
        return oldValue;
    }
}