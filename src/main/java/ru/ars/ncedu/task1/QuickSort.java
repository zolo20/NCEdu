package ru.ars.ncedu.task1;

import java.util.Comparator;

class QuickSort {

    private QuickSort() {
    }

    static <T extends Comparable<? super T>> void sort(T array[], Comparator<? super T> cmp) {
        if (array != null && array.length != 0) {
            sort(array, 0, array.length - 1, cmp);
        }
    }

    private static <T extends Comparable<? super T>> void sort(T array[], int leftBorder,
                                                               int rightBorder, Comparator<? super T> cmp) {
        int wallLeft = leftBorder;
        int wallRight = rightBorder;

        int indexMid = (leftBorder + rightBorder) % 2 == 0 ?
                (leftBorder + rightBorder) / 2 :
                Math.floorDiv((rightBorder + leftBorder), 2);
        T pivot = array[indexMid];

        while (wallLeft <= wallRight) {
            while (cmp.compare(array[wallLeft], pivot) < 0) {
                wallLeft++;
            }
            while (cmp.compare(array[wallRight], pivot) > 0) {
                wallRight--;
            }

            if (wallLeft <= wallRight) {
                swap(array, wallLeft++, wallRight--);
            }
        }

        if (wallLeft < rightBorder) {
            sort(array, wallLeft, rightBorder, cmp);
        }

        if (wallRight > leftBorder) {
            sort(array, leftBorder, wallRight, cmp);
        }
    }

    static <T extends Comparable<? super T>> void sort(T array[]) {
        if (array != null && array.length != 0) {
            sort(array, 0, array.length - 1);
        }
    }

    private static <T extends Comparable<? super T>> void sort(T array[], int leftBorder, int rightBorder) {
        int wallLeft = leftBorder;
        int wallRight = rightBorder;

        int indexMid = (leftBorder + rightBorder) % 2 == 0 ?
                (leftBorder + rightBorder) / 2 :
                Math.floorDiv((rightBorder + leftBorder), 2);
        T pivot = array[indexMid];

        while (wallLeft <= wallRight) {
            while (array[wallLeft].compareTo(pivot) < 0) {
                wallLeft++;
            }
            while (array[wallRight].compareTo(pivot) > 0) {
                wallRight--;
            }

            if (wallLeft <= wallRight) {
                swap(array, wallLeft++, wallRight--);
            }
        }

        if (wallLeft < rightBorder) {
            sort(array, wallLeft, rightBorder);
        }

        if (wallRight > leftBorder) {
            sort(array, leftBorder, wallRight);
        }
    }

    private static <T extends Comparable> void swap(T array[], int wallLeft, int wallRight) {
        T tmp = array[wallLeft];
        array[wallLeft] = array[wallRight];
        array[wallRight] = tmp;
    }
}
