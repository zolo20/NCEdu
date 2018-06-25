package ru.ars.ncedu.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static java.util.Objects.requireNonNull;

public class FilesDirectory {

    private FilesDirectory() {
    }

    /**
     * Copy content directory to a target directory.
     *
     * @param sourcePath the path to the directory to copy content.
     * @param targetPath the path to the target directory.
     * @throws IOException if an I/O error occurs.
     */
    public static void copyAll(String sourcePath, String targetPath) throws IOException {
        copy(sourcePath, targetPath, ".*");
    }

    /**
     * Copy content directory to a target directory.
     * Whether files are copied whose name matches regular expression.
     *
     * @param sourcePath the path to the directory to copy content.
     * @param targetPath the path to the target directory.
     * @param regex      the regular expression to which this file name is to be matched.
     * @throws IOException if an I/O error occurs.
     */
    public static void copy(String sourcePath, String targetPath, String regex) throws IOException {
        if (sourcePath == null || targetPath == null || regex == null) {
            throw new NullPointerException();
        }
        if (Files.notExists(new File(sourcePath).toPath()) || Files.notExists(new File(targetPath).toPath())) {
            throw new IllegalArgumentException();
        }
        if (!Files.isDirectory(new File(sourcePath).toPath()) || !Files.isDirectory(new File(targetPath).toPath())) {
            throw new IllegalArgumentException();
        }

        for (File fileSource : requireNonNull(new File(sourcePath).listFiles())) {
            if (Files.isDirectory(fileSource.toPath())) {
                if (!targetPath.contains(fileSource.getAbsolutePath())) {
                    copyDirectory(fileSource, targetPath, regex);
                }
            } else {
                copyFile(fileSource, targetPath, regex);
            }
        }
    }

    //Recursively copies the directory.
    private static void copyDirectory(File fileSource, String targetPath, String regex) throws IOException {
        File dirTarget = new File(targetPath + File.separator + fileSource.getName());
        if (Files.notExists(dirTarget.toPath())) {
            if (fileSource.getName().matches(regex)) {
                Files.copy(new File(fileSource.getAbsolutePath()).toPath(), dirTarget.toPath());
                copy(fileSource.getAbsolutePath(), targetPath + File.separator + fileSource.getName(), regex);
            }
        } else {
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

    //Copies the file.
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

    /**
     * Move content directory to a target directory.
     *
     * @param sourcePath the path to the directory to move content.
     * @param targetPath the path to the target directory.
     * @throws IOException if an I/O error occurs.
     */
    public static void moveAll(String sourcePath, String targetPath) throws IOException {
        move(sourcePath, targetPath, ".*");
    }

    /**
     * Move content directory to a target directory.
     * Whether files are moved whose name matches regular expression.
     *
     * @param sourcePath the path to the directory to move content.
     * @param targetPath the path to the target directory.
     * @param regex      the regular expression to which this file name is to be matched.
     * @throws IOException if an I/O error occurs.
     */
    public static void move(String sourcePath, String targetPath, String regex) throws IOException {
        if (sourcePath == null || targetPath == null || regex == null) {
            throw new NullPointerException();
        }
        if (Files.notExists(new File(sourcePath).toPath()) || Files.notExists(new File(targetPath).toPath())) {
            throw new IllegalArgumentException();
        }
        if (!Files.isDirectory(new File(sourcePath).toPath()) || !Files.isDirectory(new File(targetPath).toPath())) {
            throw new IllegalArgumentException();
        }

        for (File fileSource : requireNonNull(new File(sourcePath).listFiles())) {
            File fileTarget = new File(targetPath + File.separator + fileSource.getName());
            if (fileSource.getName().matches(regex)) {
                if (!targetPath.contains(fileSource.getAbsolutePath())) {
                    replacementRequest(fileSource, fileTarget, regex);
                }
            }
        }
    }

    /*
     * If file exist, the user is prompted for a replacement request.
     * If users answered "yes", does replace.
     * If users answered "no", does not replace.
     * If users not answered "no" or "yes", outputs message "No correct expression".
     */
    private static void replacementRequest(File fileSource, File fileTarget, String regex) throws IOException {
        boolean exist = isExists(fileSource, fileTarget);
        if (exist) {
            System.out.println("File \"" + fileSource.getName() +
                    "\" is exists, replace a file?(expected: \"yes\" or \"no\")");

            String enterReplace = new Scanner(System.in).nextLine();
            String answerReplace = enterReplace.equalsIgnoreCase("yes") ? "yes" :
                    enterReplace.equalsIgnoreCase("no") ? "no" : "No correct expression";

            if (!answerReplace.equalsIgnoreCase("yes") && !answerReplace.equalsIgnoreCase("no")) {
                System.out.println(answerReplace + " expected \"yes\" or \"no\"");
                replacementRequest(fileSource, fileTarget, regex);
            } else {
                moveIsExistFile(fileSource, fileTarget,
                        regex, true, answerReplace.equalsIgnoreCase("yes"));
            }
        } else {
            moveIsExistFile(fileSource, fileTarget, regex, false, false);
        }
    }

    /*
     * If file exist depending on the values @param replace, the file moves or not.
     * Move file, if not exist.
     */
    private static void moveIsExistFile(File fileSource, File fileTarget, String regex,
                                        boolean exist, boolean replace) throws IOException {
        if (Files.isDirectory(fileSource.toPath())) {
            if (exist == true && replace == true) {
                if (fileSource.getName().matches(regex)) {
                    deleteDirectory(fileTarget.getAbsolutePath());
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

    //Check exist file in directory.
    private static boolean isExists(File fileSource, File fileTarget) {
        if (Files.isDirectory(fileSource.toPath())) {
            return Files.exists(fileTarget.toPath());
        } else {
            return Files.exists(fileTarget.toPath());
        }
    }

    /**
     * Delete directory.
     *
     * @param sourcePath the path to the directory to deleteDirectory.
     * @throws IOException if an I/O error occurs.
     */
    public static void deleteDirectory(String sourcePath) throws IOException {
        if (sourcePath == null) {
            throw new NullPointerException();
        }
        if (Files.notExists(new File(sourcePath).toPath()) || !Files.isDirectory(new File(sourcePath).toPath())) {
            throw new IllegalArgumentException();
        }

        File fileTarget = new File(sourcePath);
        File[] tar = requireNonNull(fileTarget.listFiles());
        if (tar.length != 0) {
            for (File target : tar) {
                if (!target.isDirectory()) {
                    Files.delete(target.toPath());
                } else {
                    deleteDirectory(target.getAbsolutePath());
                }
            }
        }
        Files.delete(fileTarget.getAbsoluteFile().toPath());
    }

    /**
     * @param sourcePath the path to the directory.
     * @return List absolute paths all content directory.
     */
    public static List<String> getAllNameFileDirectory(String sourcePath) {
        return getNameFileDirectory(sourcePath, ".*");
    }

    /**
     * @param sourcePath the path to the directory.
     * @return List absolute paths name file which the matched regular expression directory.
     */
    public static List<String> getNameFileDirectory(String sourcePath, String regex) {
        if (sourcePath == null || regex == null) {
            throw new NullPointerException();
        }
        if (Files.notExists(new File(sourcePath).toPath()) || !Files.isDirectory(new File(sourcePath).toPath())) {
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