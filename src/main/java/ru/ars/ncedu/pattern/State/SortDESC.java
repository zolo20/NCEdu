package ru.ars.ncedu.pattern.State;

class SortDESC<T> implements Sort<T> {

    @Override
    public void sort(T array) {
        System.out.println("sorted DESC");
    }
}
