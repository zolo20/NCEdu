package ru.ars.ncedu.pattern.State;


class SortASC<T> implements Sort<T> {

    @Override
    public void sort(T array) {
        System.out.println("sorted ASC");
    }
}
