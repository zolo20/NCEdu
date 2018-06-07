package ru.ars.ncedu.task2;

import java.util.Scanner;

public class MainTask2 {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose an function:");
        System.out.println("1. copy<from><to>" + "\n" +
                "2. move<from><to>" + "\n" +
                "3. getAllNameFileDirectory<from>" + "\n" +
                "4. getNameFileDirectory<from><regex>" + "\n" +
                "5. exit");
        System.out.print("Enter an function(Example: copy<C:\\Users\\123><C:\\Users\\12>): ");
        String fullNameFunction = in.nextLine().trim();

        String nameFunction = fullNameFunction.equalsIgnoreCase("exit") ? "exit" :
                fullNameFunction.substring(0, fullNameFunction.indexOf("<"));
        String from = fullNameFunction.equalsIgnoreCase("exit") ? "exit" :
                fullNameFunction.substring(fullNameFunction.indexOf("<") + 1, fullNameFunction.indexOf(">"));
        String to = fullNameFunction.equalsIgnoreCase("exit") ? "exit" :
                fullNameFunction.substring(fullNameFunction.lastIndexOf("<") + 1, fullNameFunction.lastIndexOf(">"));

        switch (nameFunction) {
            case "copy":
                FilesDirectory.copy(from, to);
                System.out.println("Copied");
                break;
            case "move":
                FilesDirectory.move(from, to);
                System.out.println("Moved");
                break;
            case "getAllNameFileDirectory":
                System.out.println(FilesDirectory.getAllNameFileDirectory(from));
                break;
            case "getNameFileDirectory":
                String regex = fullNameFunction.substring(fullNameFunction.lastIndexOf("<") + 1, fullNameFunction.lastIndexOf(">"));
                System.out.println(FilesDirectory.getNameFileDirectory(from, regex));
                break;
            case "exit":
                System.exit(0);
            default:
                System.out.println("The function name is not valid");
                main();
        }

        System.out.println("Quit the application?(Enter \"yes\" or \"no\")");
        String YorN = in.nextLine().trim();
        exitApp(YorN);
    }

    private static void exitApp(String YorN) {
        if (YorN.equalsIgnoreCase("yes")) {
            System.exit(0);
        } else if (YorN.equalsIgnoreCase("no")) {
            main();
        } else {
            System.out.println("No correct expression, expected \"yes\" or \"no\"");
            System.out.println("Quit the application?");
            YorN = new Scanner(System.in).nextLine().trim();
            exitApp(YorN);
        }
    }
}