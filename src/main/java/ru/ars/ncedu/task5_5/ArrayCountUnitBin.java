package ru.ars.ncedu.task5_5;

import java.util.Arrays;

public class ArrayCountUnitBin {
    public static void main(String[] args) {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7};
        for (int i = 0; i < arr.length; i++) {
            int count = 0;
            if (i == 0) {
                arr[i] = 1;
            } else {
                int div = arr[i]/2;
                int mod = arr[i]%2;
                if (mod == 1) count++;
                arr[i] = arr[div - 1] + count;
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
