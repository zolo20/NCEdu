package ru.ars.ncedu.task6_5;


public class HalfDuplicate {
    public static void main(String[] args) {
        int[] num = {103,14,14,14,12};
        int indexDup = 0;
        int count = 0;
        for (int i = 1; i < num.length; i++) {
            if (num[indexDup] == num[i]) {
                count++;
            } else {
                count--;
            }
            if (count == -1) {
                indexDup = i;
                count = 0;
            }
        }
        System.out.println(num[indexDup]);
    }
}