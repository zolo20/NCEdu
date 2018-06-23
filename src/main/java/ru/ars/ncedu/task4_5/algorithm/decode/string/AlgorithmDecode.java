package ru.ars.ncedu.task4_5.algorithm.decode.string;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmDecode {
    public static void main(String[] args) {
        String str = "4[ab]ke2[c]";
        String st2 = "2[ab2[c]]";
        System.out.println(expandExpression(str));
        System.out.println(expandExpression(st2));
    }

    public static String expandExpression(String line) {
        if (!line.contains("[") || !line.contains("]")) {
            return line;
        }

        List<Integer> deep = new ArrayList<>(line.length());
        int count = 0;
        for (int i = 0; i < line.toCharArray().length; i++) {
            if (line.toCharArray()[i] == '[') {
                count++;
            } else if (line.toCharArray()[i] == ']') {
                count--;
            }
            deep.add(count);
        }
        if (deep.get(0) != 0 || deep.get(deep.size() - 1) != 0) {
            throw new IllegalArgumentException();
        }

        StringBuilder lineBuilder = new StringBuilder(line);
        int exitLoop = 0;
        for (int i = 1; i < deep.size(); i++) {
            if (deep.get(i - 1) < deep.get(i)) {
                exitLoop++;
                int quantityRepeat = Integer.valueOf(line.substring(i - 1, i));
                String lineRepeat = "";
                int inputLoop = 0;
                for (int j = i + 1; deep.get(j - 1).compareTo(deep.get(j)) <= 0 || deep.get(j) != 0; j++) {
                    lineRepeat += charsLine(j, line);
                    inputLoop++;
                }
                String insertLine = repeat(quantityRepeat, lineRepeat);
                lineBuilder.replace(i - 1, (i - 1) + 3 + inputLoop, insertLine);
                if (exitLoop == 1) {
                    break;
                }
            }
        }
        return expandExpression(lineBuilder.toString());
    }

    private static String charsLine(int index, String line) {
        char[] chars = line.toCharArray();
        return String.valueOf(chars[index]);
    }

    private static String repeat(int quantityRepeat, String lineRepeat) {
        String concatLine = "";
        for (int i = 0; i < quantityRepeat; i++) {
            concatLine += lineRepeat;
        }
        return concatLine;
    }
}