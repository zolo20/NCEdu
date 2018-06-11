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
                break;
            }
            System.out.println("_________________________________________________________");

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
                System.out.println("---------------------------------------------------------");
            }
        }
    }

    private static boolean startMethod(String nameMethod, Method method) {
        Scanner in = new Scanner(System.in);
        String from;
        String to;
        String regex;
        System.out.println("In order to return to the selection menu, enter \"return\".");
        try {
            switch (nameMethod) {
                case "copy":
                    System.out.print("Enter where to copy from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (from.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.print("Enter where to copy(Example:C:\\Users\\123>):");
                    to = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (to.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.print("Enter regular expression:");
                    regex = in.nextLine().trim();
                    if (regex.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    method.invoke(null, from, to, regex);
                    System.out.println("copied");
                    System.out.println("---------------------------------------------------------");
                    break;
                case "copyAll":
                    System.out.print("Enter where to copy from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (from.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.print("Enter where to copy(Example:C:\\Users\\123>):");
                    to = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (to.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    method.invoke(null, from, to);
                    System.out.println("copied");
                    System.out.println("---------------------------------------------------------");
                    break;
                case "move":
                    System.out.print("Enter where to move from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (from.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.print("Enter where to move(Example:C:\\Users\\123>):");
                    to = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (to.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.print("Enter regular expression:");
                    regex = in.nextLine().trim();
                    if (regex.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    method.invoke(null, from, to, regex);
                    System.out.println("moved");
                    System.out.println("---------------------------------------------------------");
                    break;
                case "moveAll":
                    System.out.print("Enter where to move from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (from.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.print("Enter where to move(Example:C:\\Users\\123>):");
                    to = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (to.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    method.invoke(null, from, to);
                    System.out.println("moved");
                    System.out.println("---------------------------------------------------------");
                    break;
                case "getNameFileDirectory":
                    System.out.print("Enter where to get name file from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (from.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.print("Enter regular expression:");
                    regex = in.nextLine().trim();
                    if (regex.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.println(method.invoke(null, from, regex));
                    System.out.println("---------------------------------------------------------");
                    break;
                case "getAllNameFileDirectory":
                    System.out.print("Enter where to get name file from(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (from.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    System.out.println(method.invoke(null, from));
                    System.out.println("---------------------------------------------------------");
                    break;
                case "deleteDirectory":
                    System.out.print("Enter path to deleteDirectory(Example:C:\\Users\\123>):");
                    from = checkerIsNotExistDirectory(in.nextLine().trim(), nameMethod, method);
                    if (from.equalsIgnoreCase("return")) {
                        System.out.println("---------------------------------------------------------");
                        break;
                    }

                    method.invoke(null, from);
                    System.out.println("deleted" + "\n");
                    System.out.println("---------------------------------------------------------");
                    break;
                default:
                    System.out.println("No correct method name " + "\"" + nameMethod + "\"" + "\n");
                    System.out.println("---------------------------------------------------------");
                    break;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static String checkerIsNotExistDirectory(String path, String nameMethod, Method method) {
        if (path.equalsIgnoreCase("return")) {
            return path;
        } else if (path.equals("") || Files.notExists(new File(path).toPath()) || !Files.isDirectory(new File(path).toPath())) {
            System.out.println("Specified path " + "\"" + path + "\"" + " is not exist or is not directory");
            System.out.print("Enter an existing path:");
            path = checkerIsNotExistDirectory(new Scanner(System.in).nextLine().trim(), nameMethod, method);
        }
        return path;
    }
}