package ru.ars.ncedu.task4_5.algorithm.decode.string;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmDecode {
    public static void main(String[] args) {
        String str = "2[ab]ke2[c]";
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
            throw new InapplicableFormatException();
        }

        StringBuilder lineBuilder = new StringBuilder(line);
        for (int i = 1; i < deep.size(); i++) {
            if (deep.get(i - 1) < deep.get(i)) {
                int quantityRepeat = Integer.valueOf(line.substring(i - 1, i));
                StringBuilder lineRepeat = new StringBuilder();
                int inputLoop = 0;
                for (int j = i + 1; deep.get(j - 1).compareTo(deep.get(j)) <= 0 || deep.get(j) != 0; j++) {
                    lineRepeat.append(charsLine(j, line));
                    inputLoop++;
                }
                String insertLine = repeat(quantityRepeat, lineRepeat);
                lineBuilder.replace(i - 1, (i - 1) + 3 + inputLoop, insertLine);
                break;
            }
        }
        return expandExpression(lineBuilder.toString());
    }

    private static String charsLine(int index, String line) {
        char[] chars = line.toCharArray();
        return String.valueOf(chars[index]);
    }

    private static String repeat(int quantityRepeat, StringBuilder lineRepeat) {
        StringBuilder concatLine = new StringBuilder();
        for (int i = 0; i < quantityRepeat; i++) {
            concatLine.append(lineRepeat);
        }
        return concatLine.toString();
    }
}