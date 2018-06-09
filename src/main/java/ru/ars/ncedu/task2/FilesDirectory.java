package ru.ars.ncedu.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static java.util.Objects.requireNonNull;

class FilesDirectory {

    private FilesDirectory() {
    }

    /**
     *
     * @param sourcePath
     * @param targetPath
     * @throws IOException
     */
    public static void copyAll(String sourcePath, String targetPath) throws IOException {
        copy(sourcePath,targetPath,".*");
    }

    public static void copy(String sourcePath, String targetPath, String regex) throws IOException {
        if (sourcePath == null || targetPath == null || regex ==null) {
            throw new NullPointerException();
        }
        if (Files.notExists(new File(sourcePath).toPath()) || Files.notExists(new File(targetPath).toPath())) {
            throw new IllegalArgumentException();
        }

        for (File fileSource : requireNonNull(new File(sourcePath).listFiles())) {
            if (Files.isDirectory(fileSource.toPath())) {
                copyDirectory(fileSource, targetPath, regex);
            } else {
                copyFile(fileSource, targetPath, regex);
            }
        }
    }

    private static void copyDirectory(File fileSource, String targetPath, String regex) throws IOException {
        File dirTarget = new File(targetPath + File.separator + fileSource.getName());
        if (Files.notExists(dirTarget.toPath())){
            if (fileSource.getName().matches(regex)) {
                Files.copy(new File(fileSource.getAbsolutePath()).toPath(), dirTarget.toPath());
                copy(fileSource.getAbsolutePath(), targetPath + File.separator + fileSource.getName(), regex);
            }
        }else {
            Set<String> targetListName = new TreeSet<>(Arrays.asList(requireNonNull(new File(targetPath).list())));
            List<Integer> numberDirectoriesList = new LinkedList<>(Collections.singletonList(0));

            targetListName.forEach(nameFile -> {
                if (nameFile.contains(fileSource.getName() + "(")) {
                    int numberFileTarget = Integer.valueOf(nameFile.
                            substring(nameFile.lastIndexOf("(") + 1,
                                    nameFile.lastIndexOf(")")));
                    numberDirectoriesList.add(numberFileTarget);
                }
            });
            int numberFile = numberDirectoriesList.get(numberDirectoriesList.size() - 1);
            String duplicateCopyDir =
                    targetPath + File.separator + fileSource.getName() + "(" + (++(numberFile)) + ")" + "— copy";
            if (fileSource.getName().matches(regex)) {
                Files.copy(fileSource.toPath(),
                        new File(duplicateCopyDir).toPath());
                copy(fileSource.getAbsolutePath(), duplicateCopyDir, regex);
            }

        }
    }

    private static void copyFile(File fileSource, String targetPath, String regex) throws IOException {
        File fileTarget = new File(targetPath + File.separator + fileSource.getName());
        if (Files.notExists(fileTarget.toPath())) {
            if (fileSource.getName().matches(regex)) {
                Files.copy(fileSource.toPath(), fileTarget.toPath());
            }
        } else {
            String nameNoFormat = fileSource.getName().substring(0, fileSource.getName().indexOf("."));
            String formatFile =
                    fileSource.getName().substring(fileSource.getName().indexOf("."), fileSource.getName().length());
            Set<String> targetListName = new TreeSet<>(Arrays.asList(requireNonNull(new File(targetPath).list())));
            List<Integer> numberFilesList = new LinkedList<>(Collections.singletonList(0));

            targetListName.forEach(nameFile -> {
                if (nameFile.contains(nameNoFormat + "(")) {
                    int numberFileTarget = Integer.valueOf(nameFile.
                            substring(nameFile.lastIndexOf("(") + 1,
                                    nameFile.lastIndexOf(")")));
                    numberFilesList.add(numberFileTarget);
                }
            });
            int numberFile = numberFilesList.get(numberFilesList.size() - 1);

            if (fileSource.getName().matches(regex)) {
                Files.copy(fileSource.toPath(),
                        new File(targetPath + File.separator +
                                nameNoFormat + "(" + (++(numberFile)) + ")" + "— copy" + formatFile).toPath());
            }
        }
    }

    public static void moveAll(String sourcePath, String targetPath) throws IOException {
        move(sourcePath, targetPath, ".*");
    }

    public static void move(String sourcePath, String targetPath, String regex) throws IOException {
        if (sourcePath == null || targetPath == null || regex ==null) {
            throw new NullPointerException();
        }
        if (Files.notExists(new File(sourcePath).toPath()) || Files.notExists(new File(targetPath).toPath())) {
            throw new IllegalArgumentException();
        }

        for (File fileSource : requireNonNull(new File(sourcePath).listFiles())) {
            File fileTarget = new File(targetPath + File.separator + fileSource.getName());
            if (fileSource.getName().matches(regex)) {
                replacementRequest(fileSource,fileTarget,regex);
            }
        }
    }

    private static void replacementRequest(File fileSource, File fileTarget, String regex) throws IOException {
        boolean exist = exists(fileSource, fileTarget);
        if (exist == true) {
            System.out.println("File " + fileSource.getName() + " is exists , replace a file?(expected: \"yes\" or \"no\")");

            String enterReplace = new Scanner(System.in).nextLine();
            String answerReplace = enterReplace.equalsIgnoreCase("yes") ? "yes" :
                    enterReplace.equalsIgnoreCase("no") ? "no" : "No correct expression";

            if (!answerReplace.equalsIgnoreCase("yes") && !answerReplace.equalsIgnoreCase("no")) {
                System.out.println(answerReplace + " expected \"yes\" or \"no\"");
                replacementRequest(fileSource,fileTarget,regex);
            } else {
                moveIsExistFile(fileSource, fileTarget,
                        regex, true, answerReplace.equalsIgnoreCase("yes"));
            }
        } else {
            moveIsExistFile(fileSource, fileTarget, regex, false, false);
        }
    }

    private static void moveIsExistFile(File fileSource, File fileTarget, String regex,
                                        boolean exist, boolean replace) throws IOException {
        if (Files.isDirectory(fileSource.toPath())) {
            if (exist == true && replace == true) {
                if (fileSource.getName().matches(regex)) {
                    delete(fileTarget.getAbsolutePath());
                    Files.move(fileSource.toPath(), fileTarget.toPath(),
                            StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                }
            } else if (exist == false && replace == false) {
                if (fileSource.getName().matches(regex)) {
                    Files.move(fileSource.toPath(), fileTarget.toPath(), StandardCopyOption.ATOMIC_MOVE);
                }
            }
        } else {
            if (exist == true && replace == true) {
                if (fileSource.getName().matches(regex)) {
                    Files.move(fileSource.toPath(), fileTarget.toPath(),
                            StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                }
            } else if (exist == false && replace == false) {
                if (fileSource.getName().matches(regex)) {
                    Files.move(fileSource.toPath(), fileTarget.toPath(), StandardCopyOption.ATOMIC_MOVE);
                }
            }
        }
    }

    private static boolean exists(File fileSource, File fileTarget) {
        if (Files.isDirectory(fileSource.toPath())) {
            return Files.exists(fileTarget.toPath());
        } else {
            return Files.exists(fileTarget.toPath());
        }
    }

    public static void delete (String targetPath) throws IOException {
        File fileTarget = new File(targetPath);
        File [] tar = requireNonNull(fileTarget.listFiles());
        if (tar.length != 0) {
            for (File target : tar) {
                if (!target.isDirectory()) {
                    Files.delete(target.toPath());
                }else {
                    delete(target.getAbsolutePath());
                }
            }
        }
        Files.delete(fileTarget.getAbsoluteFile().toPath());
    }

    public static List<String> getAllNameFileDirectory(String sourcePath) {
        return getNameFileDirectory(sourcePath, ".*");
    }

    public static List<String> getNameFileDirectory(String sourcePath, String regex) {
        if (sourcePath == null || regex == null) {
            throw new NullPointerException();
        }
        if (Files.notExists(new File(sourcePath).toPath())) {
            throw new IllegalArgumentException();
        }

        File[] files = new File(sourcePath).listFiles();
        List<String> nameFiles = new LinkedList<>();
        if (files != null) {
            for (File file : files) {
                if (file.getName().matches(regex)) {
                    nameFiles.add(file.getAbsolutePath());
                }
            }
        }
        return nameFiles;
    }
}