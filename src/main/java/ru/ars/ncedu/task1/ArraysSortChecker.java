package ru.ars.ncedu.task1;

class ArraysSortChecker {

    private ArraysSortChecker() {
    }

    static <T extends Comparable<? super T>> boolean isSort(T array[]) {
        if (array == null) {
            return false;
        }

        if (array.length == 1 || array.length == 2) {
            return true;
        }

        for (int i = 1; i < array.length - 1; i++) {
            if (array[0].compareTo(array[1]) <= 0) {
                if (array[i].compareTo(array[i + 1]) <= 0) {
                    if (i == array.length - 2) {
                        return true;
                    }
                } else {
                    break;
                }
            } else if (array[0].compareTo(array[1]) >= 0) {
                if (array[i].compareTo(array[i + 1]) >= 0) {
                    if (i == array.length - 2) {
                        return true;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }
}
