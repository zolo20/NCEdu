package ru.ars.ncedu.task5_5;

import java.util.*;

public class FindDuplicate {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(2, 0, 4, 3, 1, 5));
        int val = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(Math.abs(list.get(i))) >= 0) {
                list.set(Math.abs(list.get(i)), -list.get(Math.abs(list.get(i))));
            } else {
                val = Math.abs(list.get(i));
                break;
            }
        }
        System.out.println(val);
    }
}