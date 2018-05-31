package ru.ars.ncedu;


class ArraysSortChecker {

    private ArraysSortChecker() {
    }

    static <T extends Comparable<? super T>> boolean isSort(T array[]) {
        if (array == null) {
            return false;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) <= 0) {
                if (i == array.length - 2)
                    return true;
            } else {
                break;
            }
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) >= 0) {
                if (i == array.length - 2)
                    return true;
            } else {
                break;
            }
        }

        return false;
    }
}
