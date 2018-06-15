package ru.ars.ncedu.task3_5;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1.1");
        map.put("2", "1.2");

        for (Map.Entry<String, String> elem: map.entrySet()) {
            System.out.println(elem.getKey() + elem.getValue());
        }
        List<String> dogsName = new ArrayList<>(Arrays.asList("ss", "aa", "bb"));
        List<String> catsName = new ArrayList<>(Arrays.asList("sss", "aaa", "bb"));
        List<Pet> pets = new ArrayList<>(Arrays.asList(new Pet(dogsName), new Pet(catsName)));

        System.out.println(numberUniqueNames(pets));

        Integer[] array1 = {1,1,2, 2, 3, 3, 4, 8, 9, 10};
        Integer[] array2 = {1, 3, 4, 5, 6, 7, 10};
        System.out.println(Arrays.toString(arrUnique(array1, array2)));
    }

    static Integer[] arrUnique(Integer[] array1, Integer[] array2) {
        Set<Integer> set = new TreeSet<>();
        for (int i = 1; i < array2.length; i++) {
            for (int j = 1; j < array1.length; j++) {
                if (!array2[i - 1].equals(array2[i])) {
                    if (array2[i - 1].equals(array1[j - 1])) {
                        if (array1[j - 1].equals(array1[j])) {
                            break;
                        } else {
                            set.add(array2[i - 1]);
                        }
                    } else {
                        if (j == array1.length - 1) {
                            set.add(array2[i - 1]);
                        }
                        if (i == array2.length-1){
                            if (array2[i].equals(array1[0])){
                                if (!array1[0].equals(array1[1])) {
                                    set.add(array2[i]);
                                    break;
                                }
                            }else if (array2[i].equals(array1[j])) {
                                if (j != array1.length - 1){
                                    if (!array1[j].equals(array1[j + 1])) {
                                        set.add(array2[i]);
                                    }
                                } else {
                                    if (!array1[j].equals(array1[j - 1])) {
                                        set.add(array2[i]);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (i != array2.length - 1) {
                        if (!array2[i].equals(array2[i + 1])) {
                            i++;
                        }
                    }
                }
            }
        }

        return set.toArray(new Integer[0]);
    }

    static int numberUniqueNames(List<Pet> pets) {
        Set<String> uniqueName = new HashSet<>();
        for (Pet pet : pets) {
            uniqueName.addAll(pet.names);
        }
        return uniqueName.size();
    }
}
