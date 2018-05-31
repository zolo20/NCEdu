package ru.ars.ncedu;

import java.util.Random;

public class GenerationArrayInteger {

    private GenerationArrayInteger() {
    }

    static Integer[] arrayGeneration(int lengthArray) {
        if (lengthArray < 0) {
            throw new IllegalArgumentException("No correct value lengthArray: negative lengthArray not allowed");
        }

        Integer[] array = new Integer[lengthArray];
        for (int index = 0; index < lengthArray; index++) {
            array[index] = new Random().nextInt(10_000) - 5_000;
        }
        return array;
    }
}
