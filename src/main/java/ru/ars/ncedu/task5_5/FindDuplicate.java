package ru.ars.ncedu.task5_5;

import java.util.*;

public class FindDuplicate {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 4, 4, 6, 2, 4));
        Integer value = null;
        int index = 0;
        int indexDup = 0;
        while (indexDup < list.size()) {
            if (list.size() != 1 && indexDup == list.size() - 1) {
                index++;
                indexDup = index;
            }
            indexDup++;

            if (indexDup != list.size() && list.get(index).equals(list.get(indexDup))) {
                value = list.get(index);
                break;
            }
        }
        System.out.println(value);
    }
}