package ru.ars.ncedu;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Integer[] array = GenerationArrayInteger.arrayGeneration(7);
        Integer[] arr = {3, 2, 3, 2};

        System.out.println(ArraysSortChecker.isSort(arr));

        QuickSort.sort(array);
        System.out.println(ArraysSortChecker.isSort(array));
        System.out.println(Arrays.toString(array));

        QuickSort.sort(array, (o1, o2) -> Integer.compare(o2.compareTo(o1), 0));
        System.out.println(ArraysSortChecker.isSort(array));
        System.out.println(Arrays.toString(array));
    }
}
