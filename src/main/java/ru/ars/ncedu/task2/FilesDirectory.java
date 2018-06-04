package ru.ars.ncedu.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

class FilesDirectory {

    private FilesDirectory() {
    }

    static void copy(String sourcePath, String targetPath) {
        if (sourcePath == null || targetPath == null) {
            throw new NullPointerException("An unexpected null");
        }
        if (Files.notExists(new File(sourcePath).toPath())) {
            throw new IllegalArgumentException("Directory " + sourcePath + " does not exist");
        }
        if (Files.notExists(new File(targetPath).toPath())) {
            throw new IllegalArgumentException("Directory " + sourcePath + " does not exist");
        }

        for (File file : requireNonNull(new File(sourcePath).listFiles())) {
            try {
                Files.copy(file.toPath(), new File(targetPath + File.separator + file.getName()).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void move(String sourcePath, String targetPath) {
        if (sourcePath == null || targetPath == null) {
            throw new NullPointerException("An unexpected null");
        }
        if (Files.notExists(new File(sourcePath).toPath())) {
            throw new IllegalArgumentException("Directory " + sourcePath + " does not exist");
        }
        if (Files.notExists(new File(targetPath).toPath())) {
            throw new IllegalArgumentException("Directory " + sourcePath + " does not exist");
        }

        for (File file : requireNonNull(new File(sourcePath).listFiles())) {
            try {
                Files.move(file.toPath(), new File(targetPath + File.separator + file.getName()).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    static List<String> getAllNameFileDirectory(String path) {
        return getNameFileDirectory(path, ".*");
    }

    static List<String> getNameFileDirectory(String path, String regex) {
        if (path == null || regex == null) {
            throw new NullPointerException("An unexpected null");
        }
        if (Files.notExists(new File(path).toPath())) {
            throw new IllegalArgumentException("Directory " + path + " does not exist");
        }

        File[] files = new File(path).listFiles();
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