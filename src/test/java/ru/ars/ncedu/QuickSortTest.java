package ru.ars.ncedu;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {

    @Test
    public void sortTest() {
        Integer[] array = GenerationArrayInteger.arrayGeneration(new Random().nextInt(10));
        System.out.println(Arrays.toString(array));
        QuickSort.sort(array);
        if (array.length > 0) {
            Assert.assertTrue(ArraysSortChecker.isSort(array));
        } else {
            Assert.assertFalse(ArraysSortChecker.isSort(array));
        }
    }

    @Test
    public void sortComparatorTest() {
        Integer[] array = GenerationArrayInteger.arrayGeneration(new Random().nextInt(10));
        System.out.println(Arrays.toString(array));
        QuickSort.sort(array, (o1, o2) -> Integer.compare(o2.compareTo(o1), 0));
        if (array.length > 0) {
            Assert.assertTrue(ArraysSortChecker.isSort(array));
        } else {
            Assert.assertFalse(ArraysSortChecker.isSort(array));
        }
    }
}
