package ru.ars.ncedu.task3_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

public class TestJava8 {
    public static void main(String[] args) {
        int[] numbers = {123, 456, 789, 246, 135, 802, 791};

        int sum = Arrays.stream(numbers).reduce(0, (x, y) -> (x + y));

        System.out.println("sum = " + sum);

        List<String> dogsName = new ArrayList<>(Arrays.asList("22", "asdada", "dfssdfsfsdfsdfs"));
        dogsName.stream()
                .map(s -> s.toUpperCase() + "kak")
                .forEach(System.out::println);
        //max.ifPresent(System.out::println);
    }
}
