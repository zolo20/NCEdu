package ru.ars.ncedu.sorter;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Integer[] array = GenerationArrayInteger.arrayGeneration(6);
        Integer[] arr = {3, 2, 23};

        System.out.println(ArraysSortChecker.isSort(arr));

        QuickSort.sort(array);
        System.out.println(ArraysSortChecker.isSort(array));
        System.out.println(Arrays.toString(array));

        QuickSort.sort(array, (o1, o2) -> Integer.compare(o2.compareTo(o1), 0));
        System.out.println(ArraysSortChecker.isSort(array));
        System.out.println(Arrays.toString(array));
    }
}
