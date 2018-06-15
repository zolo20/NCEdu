package ru.ars.ncedu.task3_5;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pet {
    List<String> names;

    public Pet(List<String> names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return String.valueOf(names);
    }
}