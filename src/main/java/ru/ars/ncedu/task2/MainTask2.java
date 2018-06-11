package ru.ars.ncedu.task2;

import java.io.File;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.util.Scanner;

public class MainTask2 {
    public static void main(String... args) {
        while (true) {
            System.out.println("Choose a method:");
            System.out.println("1. copy" + "\n" +
                    "2. copyAll" + "\n" +
                    "3. move" + "\n" +
                    "4. moveAll" + "\n" +
                    "5. getAllNameFileDirectory" + "\n" +
                    "6. getNameFileDirectory" + "\n" +
                    "7. deleteDirectory" + "\n" +
                    "8. exit"
            );
            System.out.print("Enter: ");
            String nameMethod = new Scanner(System.in).nextLine().trim();
            if (nameMethod.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            Class<?> clazz = FilesDirectory.class;
            Method[] methods = clazz.getDeclaredMethods();
            boolean hasEntered = false;
            for (Method method : methods) {
                try {
                    if (nameMethod.matches(method.getName())) {
                        method = clazz.getDeclaredMethod(nameMethod, method.getParameterTypes());
                        hasEntered = startMethod(nameMethod, method);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

            if (hasEntered == false) {
                System.out.println("No correct method name " + "\"" + nameMethod + "\"" + "\n");
            }
        }
    }

    private static boolean startMethod(String nameMethod, Method method) {
        Scanner in = new Scanner(System.in);
        String from;
        String to;
        String regex;
        System.out.println("In order to return to the selection menu, enter \"return\". \n");
        try {
            switch (nameMethod) {
                case "copy":
                    System.out.print("Enter where to copy from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    System.out.print("Enter where to copy(Example:C:\\Users\\123>):");
                    to = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    System.out.print("Enter regular expression:");
                    regex = in.nextLine().trim();
                    method.invoke(null, from, to, regex);
                    System.out.println("copied" + "\n");
                    break;
                case "copyAll":
                    System.out.print("Enter where to copy from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    System.out.print("Enter where to copy(Example:C:\\Users\\123>):");
                    to = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    method.invoke(null, from, to);
                    System.out.println("copied" + "\n");
                    break;
                case "move":
                    System.out.print("Enter where to move from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    System.out.print("Enter where to move(Example:C:\\Users\\123>):");
                    to = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    System.out.print("Enter regular expression:");
                    regex = in.nextLine().trim();
                    method.invoke(null, from, to, regex);
                    System.out.println("moved" + "\n");
                    break;
                case "moveAll":
                    System.out.print("Enter where to move from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    System.out.print("Enter where to move(Example:C:\\Users\\123>):");
                    to = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    method.invoke(null, from, to);
                    System.out.println("moved" + "\n");
                    break;
                case "getNameFileDirectory":
                    System.out.print("Enter where to get name file from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    System.out.print("Enter regular expression:");
                    regex = in.nextLine().trim();
                    System.out.println(method.invoke(null, from, regex) + "\n");
                    break;
                case "getAllNameFileDirectory":
                    System.out.print("Enter where to get name file from(Example:C:\\Users\\123>):");
                    from = in.nextLine().trim();
                    checkerIsNotExistDirectory(from, nameMethod, method);
                    System.out.println(method.invoke(null, from) + "\n");
                    break;
                case "deleteDirectory":
                    System.out.print("Enter path to deleteDirectory(Example:C:\\Users\\123>):");
                    from = in.nextLine().trim();
                    checkerIsNotExistDirectory(from, nameMethod, method);
                    method.invoke(null, from);
                    System.out.println("deleted" + "\n");
                    break;
                default:
                    System.out.println("No correct method name " + "\"" + nameMethod + "\"" + "\n");
                    break;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static String checkerIsNotExistDirectory(String path, String nameMethod, Method method) {
        if (path.equalsIgnoreCase("return")) {
            main();
        } else if (path.equals("") || Files.notExists(new File(path).toPath()) || !Files.isDirectory(new File(path).toPath())) {
            System.out.println("Specified path " + "\"" + path + "\"" + " is not exist or is not directory");
            System.out.print("Enter an existing path:");
            path = checkerIsNotExistDirectory(new Scanner(System.in).nextLine().trim(), nameMethod, method);
        }
        return path;
    }
}